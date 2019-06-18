package org.rxjava.service.example;

import org.rxjava.apikit.tool.ApiGenerateManager;
import org.rxjava.apikit.tool.generator.impl.JavaClientApiGenerator;
import org.rxjava.apikit.tool.generator.impl.JavaScriptApiGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.File;

/**
 * @author happy
 * 手动构建api
 */
public class RxApiBuildApplication implements CommandLineRunner {
    /**
     * 构建api
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxApiBuildApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        //模块绝对路径
        String moduleAbsolutePath = getModuleAbsolutePath("rxjava-apikit-example");
        //java源码文件夹
        String javaSourceDir = new File(moduleAbsolutePath, "src/main/java/").getAbsolutePath();
        //设置Java生成后的文件夹路径
        String javaOutPath = new File(moduleAbsolutePath, "src/test/java/").getAbsolutePath();
        //设置Js生成后的文件夹路径
        String jsOutPath = new File(moduleAbsolutePath, "src/test/js/").getAbsolutePath();

        //配置java客户端生成器
        JavaClientApiGenerator javaClientApiGenerator = new JavaClientApiGenerator();
        javaClientApiGenerator.setOutRootPackage("org.rxjava.api.example");
        javaClientApiGenerator.setOutPath(javaOutPath);

        //配置js客户端生成器
        JavaScriptApiGenerator javaScriptApiGenerator = new JavaScriptApiGenerator();
        javaScriptApiGenerator.setOutPath(jsOutPath);
        javaScriptApiGenerator.setServiceId("example");

        //分析api和param
        ApiGenerateManager manager = ApiGenerateManager.analyse(javaSourceDir, "org.rxjava.service.example");

        //生成java客户端Api
        manager.generate(javaClientApiGenerator);

        //生成java script客户端Api
        manager.generate(javaScriptApiGenerator);
    }

    /**
     * 获取指定模块绝对路径
     */
    private String getModuleAbsolutePath(String module) {
        File root = new File(module);
        if (!root.exists()) {
            root = new File("rxjava-example/", module);
        }
        if (!root.exists()) {
            root = new File(".");
        }
        return root.getAbsolutePath();
    }

}
