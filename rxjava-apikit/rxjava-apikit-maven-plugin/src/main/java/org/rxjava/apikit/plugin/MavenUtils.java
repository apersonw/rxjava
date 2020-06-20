package org.rxjava.apikit.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.rxjava.apikit.plugin.bean.Group;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author happy 2019-01-05 15:40
 */
public class MavenUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.activateDefaultTyping(BasicPolymorphicTypeValidator.builder().build(), ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public static void generate(MavenProject project, Group group, String sourcePath, String[] srcPaths) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader loader = getUrlClassLoader(project);
            @SuppressWarnings("unchecked")
            Class<GenerateUtils> generateUtilsClass = (Class<GenerateUtils>) loader
                    .loadClass(GenerateUtils.class.getName());
            Method generateMethod = generateUtilsClass.getMethod(
                    "generate",
                    String.class,
                    String.class,
                    String[].class
            );
            String groupJson = serialize(group);
            /*
             * 切换后续操作的classLoad加载器
             */
            Thread.currentThread().setContextClassLoader(loader);
            generateMethod.invoke(null, groupJson, sourcePath, srcPaths);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //还原
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }


    public static String serialize(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    private static URLClassLoader getUrlClassLoader(MavenProject project) throws DependencyResolutionRequiredException {
        List<String> compileClasspathElements = project.getCompileClasspathElements();
        List<String> systemClasspathElements = project.getSystemClasspathElements();
        ClassRealm classLoader = (ClassRealm) MavenUtils.class.getClassLoader();

        Optional<ClassRealm> apiClassRealm = classLoader.getImportRealms().stream().filter((p) -> "maven.api".equals(p.getId())).findFirst();

        URL[] urls = Stream.concat(
                Stream.concat(
                        compileClasspathElements.stream().map(MavenUtils::toUrl),
                        systemClasspathElements.stream().map(MavenUtils::toUrl)
                ),
                Stream.of(classLoader.getURLs())
        ).toArray(URL[]::new);

        return apiClassRealm
                .map(classRealm -> new URLClassLoader(urls, classRealm))
                .orElseGet(() -> new URLClassLoader(urls));
    }


    private static URL toUrl(String spec) {
        try {
            return Paths.get(spec).toUri().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
