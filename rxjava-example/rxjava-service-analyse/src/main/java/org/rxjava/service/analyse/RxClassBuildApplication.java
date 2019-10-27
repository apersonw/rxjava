package org.rxjava.service.analyse;

import org.rxjava.apikit.tool.analyse.impl.ApiAnalyse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.File;

/**
 * @author happy
 * 手动构建api
 */
public class RxClassBuildApplication implements CommandLineRunner {
    /**
     * 构建api
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxClassBuildApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(String... args) {
        //模块绝对路径
        String moduleAbsolutePath = getModuleAbsolutePath("rxjava-service-analyse");
        //java源码文件夹
        String javaSourceDir = new File(moduleAbsolutePath, "src/main/java/").getAbsolutePath();
        //设置Java生成后的文件夹路径
        String javaOutPath = new File(moduleAbsolutePath, "src/test/java/").getAbsolutePath();
        //设置Js生成后的文件夹路径
        String jsOutPath = new File(moduleAbsolutePath, "src/test/js/").getAbsolutePath();
        //设置Js生成后的文件夹路径
        String apidocOutPath = new File(moduleAbsolutePath, "src/test/apidoc/").getAbsolutePath();

        //分析
        ApiAnalyse analyse = new ApiAnalyse();
        analyse.analyse("org.rxjava.service.analyse");
    }

    /**
     * 获取指定模块绝对路径
     */
    private String getModuleAbsolutePath(String module) {
        File root = new File(module);
        if (!root.exists()) {
            root = new File("rxjava/rxjava-analyse/", module);
        }
        if (!root.exists()) {
            root = new File("rxjava-analyse/", module);
        }
        if (!root.exists()) {
            root = new File(".");
        }
        return root.getAbsolutePath();
    }

}
