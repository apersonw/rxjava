package org.rxjava.service.example;

import org.rxjava.apikit.tool.ApiGenerateManager;
import org.rxjava.apikit.tool.generator.impl.ApidocApiGenerator;
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
        String moduleAbsolutePath = getModuleAbsolutePath("rxjava-service-example");
        //java源码文件夹
        String javaSourceDir = new File(moduleAbsolutePath, "src/main/java/").getAbsolutePath();
        //设置Java生成后的文件夹路径
        String javaOutPath = new File(moduleAbsolutePath, "src/test/java/").getAbsolutePath();
        //设置Js生成后的文件夹路径
        String jsOutPath = new File(moduleAbsolutePath, "src/test/js/").getAbsolutePath();
        //设置Js生成后的文件夹路径
        String apidocOutPath = new File(moduleAbsolutePath, "src/test/apidoc/").getAbsolutePath();

        //分析api和param
        ApiGenerateManager manager = ApiGenerateManager.analyse(javaSourceDir, "org.rxjava.service.example");
        //{
        //    //配置java客户端生成器
        //    JavaClientApiGenerator javaClientApiGenerator = new JavaClientApiGenerator();
        //    javaClientApiGenerator.setOutRootPackage("org.rxjava.api.example");
        //    javaClientApiGenerator.setOutPath(javaOutPath);
        //
        //    //生成java客户端Api
        //    manager.generate(javaClientApiGenerator);
        //}

        {
            //配置js客户端生成器
            JavaScriptApiGenerator javaScriptApiGenerator = new JavaScriptApiGenerator();
            javaScriptApiGenerator.setOutPath(jsOutPath);
            javaScriptApiGenerator.setServiceId("example");

            //生成java script客户端Api
            manager.generate(javaScriptApiGenerator);
        }

        //{
        //    //配置apidoc生成器
        //    ApidocApiGenerator apidocApiGenerator = new ApidocApiGenerator();
        //    apidocApiGenerator.setOutPath(apidocOutPath);
        //    apidocApiGenerator.setServiceId("example");
        //
        //    //生成apidoc
        //    manager.generate(apidocApiGenerator);
        //}
    }

    /**
     * 获取指定模块绝对路径
     */
    private String getModuleAbsolutePath(String module) {
        File root = new File(module);
        if (!root.exists()) {
            root = new File("rxjava/rxjava-example/", module);
        }
        if (!root.exists()) {
            root = new File("rxjava-example/", module);
        }
        if (!root.exists()) {
            root = new File(".");
        }
        return root.getAbsolutePath();
    }

}
