package top.rxjava.apikit.plugin;

import top.rxjava.apikit.plugin.bean.Group;
import top.rxjava.apikit.plugin.bean.Task;
import top.rxjava.apikit.plugin.utils.MavenUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.util.List;
import java.util.Map;

/**
 * @author happy
 */
@Mojo(
        name = "api",
        requiresDependencyCollection = ResolutionScope.RUNTIME,
        requiresDependencyResolution = ResolutionScope.RUNTIME
)
public class ApikitMojo extends AbstractMojo {
    @Parameter
    private List<Task> tasks;

    @Parameter
    private String rootPackage;

    @Parameter
    private String apiType;

    @Parameter
    private boolean reactive;

    @Override
    public void execute() {
        Map<?, ?> pluginContext = getPluginContext();
        MavenProject project = (MavenProject) pluginContext.get("project");
        String[] compileSourceRoots = project
                .getCompileSourceRoots()
                .stream()
                .filter(str -> !str.contains("generated-sources/annotations"))
                .toArray(String[]::new);

        if (compileSourceRoots.length > 1) {
            throw new RuntimeException("Multiple compileSourceRoot is not supported");
        }
        //获取到java源码路径
        String javaSourcePath = compileSourceRoots[0];

        getLog().info("开始执行全部任务" + tasks + pluginContext);

        //rootPackage截取部分字符串
        rootPackage = rootPackage.replace("rxjava-service", "");
        MavenUtils.generate(project, new Group(tasks, rootPackage, apiType, reactive), javaSourcePath, compileSourceRoots);
    }
}
