package top.rxjava.apikit.plugin.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.type.TypeFactory;
import top.rxjava.apikit.plugin.bean.*;
import top.rxjava.apikit.tool.ApiGenerateManager;
import top.rxjava.apikit.tool.generator.AbstractGenerator;
import top.rxjava.apikit.tool.generator.Generator;
import top.rxjava.apikit.tool.generator.impl.GitGenerator;
import top.rxjava.apikit.tool.generator.impl.JavaClientApiGenerator;
import top.rxjava.apikit.tool.generator.impl.JavaScriptApiGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

import java.util.List;

/**
 * @author happy
 */
public class GenerateUtils {
    public static <T> T deserialize(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
            TypeFactory tf = TypeFactory.defaultInstance()
                    .withClassLoader(GenerateUtils.class.getClassLoader());
            objectMapper.setTypeFactory(tf);
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String groupJson = "[\"top.rxjava.apikit.plugin.bean.Group\",{\"tasks\":[\"java.util.ArrayList\",[[\"top.rxjava.apikit.plugin.bean.GitTask\",{\"outPath\":\"example/java/rxjava-apis-example-person/src/main/java\",\"url\":\"https://github.com/apersonw/rxjava-apis.git\",\"user\":null,\"password\":null,\"authorEmail\":\"apikit@rxjava.org\",\"authorName\":\"apikit\",\"task\":[\"top.rxjava.apikit.plugin.bean.JavaClientTask\",{\"outPath\":null,\"nameMaperSource\":null,\"nameMaperDist\":null,\"outRootPackage\":\"top.rxjava.api.person.example\"}],\"branch\":\"master\",\"deleteUris\":[\"java.util.ArrayList\",[\"org\"]]}],[\"top.rxjava.apikit.plugin.bean.GitTask\",{\"outPath\":\"example/js/rxjava-apis-example-person\",\"url\":\"https://github.com/apersonw/rxjava-apis.git\",\"user\":null,\"password\":null,\"authorEmail\":\"apikit@rxjava.org\",\"authorName\":\"apikit\",\"task\":[\"top.rxjava.apikit.plugin.bean.JavaScriptClientTask\",{\"outPath\":null,\"nameMaperSource\":null,\"nameMaperDist\":null,\"outRootPackage\":null,\"serviceId\":\"example\"}],\"branch\":\"master\",\"deleteUris\":[\"java.util.ArrayList\",[\"org\"]]}]]],\"rootPackage\":\"top.rxjava.service.example.person\",\"apiType\":\"person\"}]";
//        String groupJson = "[\"top.rxjava.apikit.plugin.bean.Group\",{\"tasks\":[],\"rootPackage\":\"top.rxjava.service.example.person\",\"apiType\":\"person\"}]";
        Group deserialize = GenerateUtils.deserialize(groupJson, Group.class);
    }

    public static void generate(String groupJson, String sourcePath, String[] srcPaths) {
        Group group = deserialize(groupJson, Group.class);
        generate(group, sourcePath, srcPaths, new SystemStreamLog());
    }

    public static void generate(Group group, String javaSourcePath, String[] srcPaths, Log log) {
        List<Task> tasks = group.getTasks();
        boolean reactive = group.isReactive();

        if (CollectionUtils.isEmpty(tasks)) {
            log.info("当前没有任务");
            return;
        }

        try {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                log.info("开始执行任务,number:" + (i + 1) + ",type:" + task.getClass().getSimpleName() + ",task:" + task);

                //源码所在根包路径
                String rootPackage = group.getRootPackage();
                String apiType = group.getApiType();

                if (task instanceof GitTask gitTask) {
                    GitGenerator gitGenerator = new GitGenerator();

                    //设置git信息
                    gitGenerator.setGitUrl(gitTask.getUrl());
                    gitGenerator.setGitUser(gitTask.getUser());
                    gitGenerator.setGitPassword(gitTask.getPassword());
                    gitGenerator.setGitEmail(gitTask.getAuthorEmail());
                    gitGenerator.setGitName(gitTask.getAuthorName());
                    gitGenerator.setGitBranch(gitTask.getBranch());

                    //初始化api生成管理器
                    ApiGenerateManager manager = ApiGenerateManager.analyse(javaSourcePath, rootPackage);
                    Generator generator = createGenerator(gitTask.getTask(), apiType);
                    gitGenerator.setGenerator((AbstractGenerator) generator);

                    //设置输出文件夹和需要删除的文件夹
                    gitGenerator.setOutPath(gitTask.getOutPath());
                    gitGenerator.setDeleteUris(gitTask.getDeleteUris());
                    manager.generate(gitGenerator);
                }
                log.info("结束执行任务:" + (i + 1) + "type:" + task.getClass().getSimpleName());
            }
            log.info("所有任务执行完毕");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据任务类型创建生成器
    private static Generator createGenerator(Task task, String apiType) {
        //执行生成java客户端Api任务
        if (task instanceof JavaClientTask javaClientTask) {
            JavaClientApiGenerator javaClientApiGenerator = new JavaClientApiGenerator();

            String projectName = javaClientTask.getProjectName();
            String moduleName = projectName.replace("rxjava-service-", "");
            //设置生成的api根包路径
            String outRootPackage = javaClientTask.getOutRootPackage().replace("top.rxjava", "top.rxjava." + moduleName);
            javaClientApiGenerator.setOutRootPackage(outRootPackage);
            javaClientApiGenerator.setArtifactId(javaClientTask.getArtifactId());
            javaClientApiGenerator.setParentVersion(javaClientTask.getParentVersion());
            return javaClientApiGenerator;
        }
        //执行生成js客户端Api任务
        if (task instanceof JavaScriptClientTask javaClientTask) {
            JavaScriptApiGenerator javaScriptApiGenerator = new JavaScriptApiGenerator();

            //设置生成的api根包路径
            javaScriptApiGenerator.setOutRootPackage(javaClientTask.getOutRootPackage());
            javaScriptApiGenerator.setServiceId(javaClientTask.getServiceId());
            javaScriptApiGenerator.setApiType(apiType);
            return javaScriptApiGenerator;
        }
        throw new RuntimeException("错误的任务类型:" + task.getClass() + ",task:" + task);
    }
}
