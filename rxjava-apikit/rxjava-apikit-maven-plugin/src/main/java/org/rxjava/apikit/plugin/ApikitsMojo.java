package org.rxjava.apikit.plugin;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.rxjava.apikit.plugin.bean.Group;

import java.util.List;

@Mojo(
        name = "apis",
        requiresDependencyCollection = ResolutionScope.RUNTIME,
        requiresDependencyResolution = ResolutionScope.RUNTIME
)
public class ApikitsMojo extends AbstractMojo {
    @Parameter
    private List<Group> groups;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;

    @Override
    public void execute() {
        MavenProject project = session.getCurrentProject();
        String[] compileSourceRoots = project
                .getCompileSourceRoots()
                .stream()
                .filter(str -> !str.contains("generated-sources/annotations"))
                .toArray(String[]::new);


        if (compileSourceRoots.length > 1) {
            throw new RuntimeException("Multiple compileSourceRoot is not supported");
        }
        String sourcePath = compileSourceRoots[0];

        try {
            getLog().info("ApikitsMojo任务启动");
            for (int i = 0; i < groups.size(); i++) {
                Group group = groups.get(i);
                int y = i + 1;
                getLog().info("开始执行第" + y + "组" + group);
                MavenUtils.generate(project, group, sourcePath, compileSourceRoots);
                getLog().info("结束第" + y + "组");
            }
            getLog().info("ApikitsMojo任务执行完毕");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
