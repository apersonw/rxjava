package org.rxjava.apikit.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.rxjava.apikit.plugin.bean.*;
import org.rxjava.apikit.tool.ApiGenerateManager;
import org.rxjava.apikit.tool.generator.AbstractGenerator;
import org.rxjava.apikit.tool.generator.Generator;
import org.rxjava.apikit.tool.generator.impl.GitGenerator;
import org.rxjava.apikit.tool.generator.impl.JavaClientApiGenerator;
import org.rxjava.apikit.tool.generator.impl.JavaScriptApiGenerator;

import java.util.List;

/**
 * @author happy
 */
public class GenerateUtils {
    public static <T> T deserialize(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = JsonMapper.builder().build().enableDefaultTyping(BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build(), ObjectMapper.DefaultTyping.NON_FINAL);
            TypeFactory tf = TypeFactory.defaultInstance()
                    .withClassLoader(GenerateUtils.class.getClassLoader());
            objectMapper.setTypeFactory(tf);
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void generate(String groupJson, String sourcePath, String[] srcPaths) {
        Group group = deserialize(groupJson, Group.class);
        generate(group, sourcePath, srcPaths, new SystemStreamLog());
    }

    public static void generate(Group group, String javaSourcePath, String[] srcPaths, Log log) {
        List<Task> tasks = group.getTasks();

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

                if (task instanceof GitTask) {
                    GitTask gitTask = (GitTask) task;
                    GitGenerator gitGenerator = new GitGenerator();

                    //设置git信息
                    gitGenerator.setGitUrl(gitTask.getUrl());
                    gitGenerator.setGitUser(gitTask.getUser());
                    gitGenerator.setGetPassword(gitTask.getPassword());
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
        if (task instanceof JavaClientTask) {
            JavaClientTask javaClientTask = (JavaClientTask) task;
            JavaClientApiGenerator javaClientApiGenerator = new JavaClientApiGenerator();

            //设置生成的api根包路径
            javaClientApiGenerator.setOutRootPackage(javaClientTask.getOutRootPackage());
            return javaClientApiGenerator;
        }
        //执行生成js客户端Api任务
        if (task instanceof JavaScriptClientTask) {
            JavaScriptClientTask javaClientTask = (JavaScriptClientTask) task;
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
