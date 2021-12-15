package top.rxjava.apikit.plugin;

import top.rxjava.apikit.plugin.bean.Group;
import top.rxjava.apikit.plugin.utils.MavenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;

@Mojo(
        name = "apis",
        requiresDependencyCollection = ResolutionScope.RUNTIME,
        requiresDependencyResolution = ResolutionScope.RUNTIME
)
@Slf4j
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
                .filter(str -> !str.contains("generated-sources\\annotations"))
                .toArray(String[]::new);

        if (compileSourceRoots.length > 1) {
            throw new RuntimeException("不支持多编译源码路径");
        }
        String sourcePath = compileSourceRoots[0];
        try {
            //截取rxjava-service为空字符串
            List<Group> newGroups = new ArrayList<>();
            groups.forEach(group -> {
                group.setRootPackage(group.getRootPackage().replace("rxjava-service-",""));
                newGroups.add(group);
            });
            log.info("开始执行全部任务:" + newGroups);
            for (int i = 0; i < newGroups.size(); i++) {
                Group group = newGroups.get(i);
                log.info("开始执行第" + i + "组:" + group);
                MavenUtils.generate(project, group, sourcePath, compileSourceRoots);
                log.info("执行完成第" + i + "组:" + group);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
