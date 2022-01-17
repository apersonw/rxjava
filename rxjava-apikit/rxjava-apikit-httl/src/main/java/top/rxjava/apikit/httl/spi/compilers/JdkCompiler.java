/*
 * Copyright 2011-2013 HTTL Team.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.rxjava.apikit.httl.spi.compilers;

import top.rxjava.apikit.httl.spi.Compiler;
import top.rxjava.apikit.httl.util.ClassUtils;
import top.rxjava.apikit.httl.util.StringUtils;
import top.rxjava.apikit.httl.util.UnsafeByteArrayInputStream;
import top.rxjava.apikit.httl.util.UnsafeByteArrayOutputStream;

import javax.tools.*;
import javax.tools.JavaFileObject.Kind;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

/**
 * JdkCompiler. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.translators.CompiledTranslator#setCompiler(Compiler)
 */
public class JdkCompiler extends AbstractCompiler {

    private final JavaCompiler compiler;

    private final DiagnosticCollector<JavaFileObject> diagnosticCollector;

    private final StandardJavaFileManager standardJavaFileManager;

    private final ClassLoaderImpl classLoader;

    private final JavaFileManagerImpl javaFileManager;

    private final List<String> options = new ArrayList<String>();

    private final List<String> lintOptions = new ArrayList<String>();

    private boolean lintUnchecked;

    public JdkCompiler() {
        compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Can not get system java compiler. Please run with JDK (NOT JVM), or configure the httl.properties: compiler=httl.spi.compilers.JavassistCompiler, and add javassist.jar.");
        }
        diagnosticCollector = new DiagnosticCollector<JavaFileObject>();
        standardJavaFileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        try {
            contextLoader.loadClass(JdkCompiler.class.getName());
        } catch (ClassNotFoundException e) { // 如果线程上下文的ClassLoader不能加载当前httl.jar包中的类，则切换回httl.jar所在的ClassLoader
            contextLoader = JdkCompiler.class.getClassLoader();
        }
        ClassLoader loader = contextLoader;
        Set<File> files = new HashSet<File>();
        while (loader instanceof URLClassLoader
                && (!loader.getClass().getName().equals("sun.misc.Launcher$AppClassLoader"))) {
            URLClassLoader urlClassLoader = (URLClassLoader) loader;
            for (URL url : urlClassLoader.getURLs()) {
                files.add(new File(url.getFile()));
            }
            loader = loader.getParent();
        }
        if (files.size() > 0) {
            try {
                Iterable<? extends File> list = standardJavaFileManager.getLocation(StandardLocation.CLASS_PATH);
                for (File file : list) {
                    files.add(file);
                }
                standardJavaFileManager.setLocation(StandardLocation.CLASS_PATH, files);
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        final ClassLoader parentLoader = contextLoader;
        classLoader = AccessController.doPrivileged((PrivilegedAction<ClassLoaderImpl>) () -> new ClassLoaderImpl(parentLoader));
        javaFileManager = new JavaFileManagerImpl(standardJavaFileManager, classLoader);
        lintOptions.add("-Xlint:unchecked");
    }

    public void init() {
        if (logger != null && logger.isDebugEnabled()) {
            StringBuilder buf = new StringBuilder(320);
            buf.append("JDK Compiler classpath locations:\n");
            buf.append("================\n");
            for (File file : standardJavaFileManager.getLocation(StandardLocation.CLASS_PATH)) {
                buf.append(file.getAbsolutePath());
                buf.append("\n");
            }
            buf.append("================\n");
            logger.debug(buf.toString());
        }
    }

    /**
     * httl.properties: java.specification.version=1.7
     */
    public void setCompileVersion(String version) {
        if (StringUtils.isNotEmpty(version)
                && !version.equals(ClassUtils.getJavaVersion())) {
            options.add("-target");
            options.add(version);
            lintOptions.add("-target");
            lintOptions.add(version);
        }
    }

    /**
     * httl.properties: lint.unchecked=true
     */
    public void setLintUnchecked(boolean lintUnchecked) {
        this.lintUnchecked = lintUnchecked;
    }

    @Override
    protected Class<?> doCompile(String name, String sourceCode) throws Exception {
        try {
            return doCompile(name, sourceCode, options);
        } catch (Exception e) {
            if (lintUnchecked && e.getMessage() != null
                    && e.getMessage().contains("-Xlint:unchecked")) {
                try {
                    return doCompile(name, sourceCode, lintOptions);
                } catch (Exception e2) {
                    throw e2;
                }
            }
            throw e;
        }
    }

    private Class<?> doCompile(String name, String sourceCode, List<String> options) throws Exception {
        try {
            return classLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            int i = name.lastIndexOf('.');
            String packageName = i < 0 ? "" : name.substring(0, i);
            String className = i < 0 ? name : name.substring(i + 1);
            JavaFileObjectImpl javaFileObject = new JavaFileObjectImpl(className, sourceCode);
            javaFileManager.putFileForInput(StandardLocation.SOURCE_PATH, packageName,
                    className + ClassUtils.JAVA_EXTENSION, javaFileObject);
            Boolean result = compiler.getTask(null, javaFileManager, diagnosticCollector, options,
                    null, Arrays.asList(javaFileObject)).call();
            if (result == null || !result) {
                throw new IllegalStateException("Compilation failed. class: " + name + ", diagnostics: " + diagnosticCollector.getDiagnostics());
            }
            if (compileDirectory != null) {
                saveBytecode(name, javaFileObject.getByteCode());
            }
            return classLoader.loadClass(name);
        }
    }

    private static final class JavaFileObjectImpl extends SimpleJavaFileObject {

        private final CharSequence source;
        private UnsafeByteArrayOutputStream bytecode;

        public JavaFileObjectImpl(final String baseName, final CharSequence source) {
            super(ClassUtils.toURI(baseName + ClassUtils.JAVA_EXTENSION), Kind.SOURCE);
            this.source = source;
        }

        JavaFileObjectImpl(final String name, final Kind kind) {
            super(ClassUtils.toURI(name), kind);
            source = null;
        }

        public JavaFileObjectImpl(URI uri, Kind kind) {
            super(uri, kind);
            source = null;
        }

        @Override
        public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws UnsupportedOperationException {
            if (source == null) {
                throw new UnsupportedOperationException("source == null");
            }
            return source;
        }

        @Override
        public InputStream openInputStream() {
            return new UnsafeByteArrayInputStream(getByteCode());
        }

        @Override
        public OutputStream openOutputStream() {
            return bytecode = new UnsafeByteArrayOutputStream();
        }

        public byte[] getByteCode() {
            return bytecode.toByteArray();
        }
    }

    private static final class JavaFileManagerImpl extends ForwardingJavaFileManager<JavaFileManager> {

        private final ClassLoaderImpl classLoader;

        private final Map<URI, JavaFileObject> fileObjects = new HashMap<URI, JavaFileObject>();

        public JavaFileManagerImpl(JavaFileManager fileManager, ClassLoaderImpl classLoader) {
            super(fileManager);
            this.classLoader = classLoader;
        }

        @Override
        public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
            FileObject o = fileObjects.get(uri(location, packageName, relativeName));
            if (o != null)
                return o;
            return super.getFileForInput(location, packageName, relativeName);
        }

        public void putFileForInput(StandardLocation location, String packageName, String relativeName, JavaFileObject file) {
            fileObjects.put(uri(location, packageName, relativeName), file);
        }

        private URI uri(Location location, String packageName, String relativeName) {
            return ClassUtils.toURI(location.getName() + '/' + packageName + '/' + relativeName);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String qualifiedName, Kind kind, FileObject outputFile)
                throws IOException {
            JavaFileObject file = new JavaFileObjectImpl(qualifiedName, kind);
            classLoader.add(qualifiedName, file);
            return file;
        }

        @Override
        public ClassLoader getClassLoader(JavaFileManager.Location location) {
            return classLoader;
        }

        @Override
        public String inferBinaryName(Location loc, JavaFileObject file) {
            if (file instanceof JavaFileObjectImpl)
                return file.getName();
            return super.inferBinaryName(loc, file);
        }

        @Override
        public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse)
                throws IOException {
            ArrayList<JavaFileObject> files = new ArrayList<JavaFileObject>();
            if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS)) {
                for (JavaFileObject file : fileObjects.values()) {
                    if (file.getKind() == Kind.CLASS && file.getName().startsWith(packageName)) {
                        files.add(file);
                    }
                }
                files.addAll(classLoader.files());
            } else if (location == StandardLocation.SOURCE_PATH && kinds.contains(JavaFileObject.Kind.SOURCE)) {
                for (JavaFileObject file : fileObjects.values()) {
                    if (file.getKind() == Kind.SOURCE && file.getName().startsWith(packageName)) {
                        files.add(file);
                    }
                }
            }
            Iterable<JavaFileObject> result = super.list(location, packageName, kinds, recurse);
            for (JavaFileObject file : result) {
                files.add(file);
            }
            return files;
        }
    }

    private final class ClassLoaderImpl extends ClassLoader {

        private final Map<String, JavaFileObject> classes = new HashMap<String, JavaFileObject>();

        ClassLoaderImpl(final ClassLoader parentClassLoader) {
            super(parentClassLoader);
        }

        Collection<JavaFileObject> files() {
            return Collections.unmodifiableCollection(classes.values());
        }

        @Override
        protected Class<?> findClass(final String qualifiedClassName) throws ClassNotFoundException {
            try {
                return super.findClass(qualifiedClassName);
            } catch (ClassNotFoundException e) {
                JavaFileObject file = classes.get(qualifiedClassName);
                if (file != null) {
                    byte[] bytes = ((JavaFileObjectImpl) file).getByteCode();
                    return defineClass(qualifiedClassName, bytes, 0, bytes.length);
                }
                throw e;
            }
        }

        void add(final String qualifiedClassName, final JavaFileObject javaFile) {
            classes.put(qualifiedClassName, javaFile);
        }

        @Override
        public InputStream getResourceAsStream(final String name) {
            if (name.endsWith(ClassUtils.CLASS_EXTENSION)) {
                String qualifiedClassName = name.substring(0, name.length() - ClassUtils.CLASS_EXTENSION.length()).replace('/', '.');
                JavaFileObjectImpl file = (JavaFileObjectImpl) classes.get(qualifiedClassName);
                if (file != null) {
                    return new UnsafeByteArrayInputStream(file.getByteCode());
                }
            }
            return super.getResourceAsStream(name);
        }
    }


}