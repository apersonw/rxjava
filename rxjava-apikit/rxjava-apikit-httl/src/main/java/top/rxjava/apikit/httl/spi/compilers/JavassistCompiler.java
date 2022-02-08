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

import javassist.*;
import top.rxjava.apikit.httl.spi.Compiler;
import top.rxjava.apikit.httl.spi.translators.CompiledTranslator;
import top.rxjava.apikit.httl.util.ClassUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JavassistCompiler. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see CompiledTranslator#setCompiler(Compiler)
 */
public class JavassistCompiler extends AbstractCompiler {

    private static final Pattern IMPORT_PATTERN = Pattern.compile("import\\s+([\\w\\.\\*]+);\n");

    private static final Pattern EXTENDS_PATTERN = Pattern.compile("\\s+extends\\s+([\\w\\.]+)[^\\{]*\\{\n");

    private static final Pattern IMPLEMENTS_PATTERN = Pattern.compile("\\s+implements\\s+([\\w\\.]+)\\s*\\{\n");

    private static final Pattern METHODS_PATTERN = Pattern.compile("\n(private|public|protected)\\s+");

    private static final Pattern FIELD_PATTERN = Pattern.compile("[^\n]+=[^\n]+;");

    private final byte[] locker = new byte[0];

    protected ClassPool pool;

    public JavassistCompiler() {
        init();
    }

    protected void init() {
        pool = ClassPool.getDefault();
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        try {
            contextLoader.loadClass(JdkCompiler.class.getName());
        } catch (ClassNotFoundException e) { // 如果线程上下文的ClassLoader不能加载当前httl.jar包中的类，则切换回httl.jar所在的ClassLoader
            contextLoader = JdkCompiler.class.getClassLoader();
        }
        pool.appendClassPath(new LoaderClassPath(contextLoader));
    }

    @Override
    protected Class<?> doCompile(String name, String source) throws Exception {
        synchronized (locker) {
            try {
                return pool.get(name).toClass(JavassistCompiler.class.getClassLoader(), null);
            } catch (NotFoundException e) {
                int i = name.lastIndexOf('.');
                String className = i < 0 ? name : name.substring(i + 1);
                Matcher matcher = IMPORT_PATTERN.matcher(source);
                List<String> importPackages = new ArrayList<String>();
                Map<String, String> fullNames = new HashMap<String, String>();
                while (matcher.find()) {
                    String pkg = matcher.group(1);
                    if (pkg.endsWith(".*")) {
                        String pkgName = pkg.substring(0, pkg.length() - 2);
                        pool.importPackage(pkgName);
                        importPackages.add(pkgName);
                    } else {
                        pool.importPackage(pkg);
                        int pi = pkg.lastIndexOf('.');
                        fullNames.put(pi < 0 ? pkg : pkg.substring(pi + 1), pkg);
                    }
                }
                String[] packages = importPackages.toArray(new String[0]);
                matcher = EXTENDS_PATTERN.matcher(source);
                CtClass cls;
                if (matcher.find()) {
                    String extend = matcher.group(1).trim();
                    String extendClass;
                    if (extend.contains(".")) {
                        extendClass = extend;
                    } else if (fullNames.containsKey(extend)) {
                        extendClass = fullNames.get(extend);
                    } else {
                        extendClass = ClassUtils.forName(packages, extend).getName();
                    }
                    cls = pool.makeClass(name, pool.get(extendClass));
                } else {
                    cls = pool.makeClass(name);
                }
                matcher = IMPLEMENTS_PATTERN.matcher(source);
                if (matcher.find()) {
                    String[] ifaces = matcher.group(1).trim().split("\\,");
                    for (String iface : ifaces) {
                        iface = iface.trim();
                        String ifaceClass;
                        if (iface.contains(".")) {
                            ifaceClass = iface;
                        } else if (fullNames.containsKey(iface)) {
                            ifaceClass = fullNames.get(iface);
                        } else {
                            ifaceClass = ClassUtils.forName(packages, iface).getName();
                        }
                        cls.addInterface(pool.get(ifaceClass));
                    }
                }
                String body = source.substring(source.indexOf("{") + 1, source.length() - 1);
                String[] methods = METHODS_PATTERN.split(body);
                for (String method : methods) {
                    method = method.trim();
                    if (method.length() > 0) {
                        if (method.startsWith(className)) {
                            cls.addConstructor(CtNewConstructor.make("public " + method, cls));
                        } else if (method.indexOf('{') < 0 || FIELD_PATTERN.matcher(method).matches()) {
                            cls.addField(CtField.make("private " + method, cls));
                        } else {
                            cls.addMethod(CtNewMethod.make("public " + method, cls));
                        }
                    }
                }
                if (compileDirectory != null) {
                    saveBytecode(name, cls.toBytecode());
                }
                return cls.toClass(JavassistCompiler.class.getClassLoader(), null);
            }
        }
    }

}