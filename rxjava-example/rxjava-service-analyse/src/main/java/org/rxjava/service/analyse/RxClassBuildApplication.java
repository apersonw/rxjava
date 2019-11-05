package org.rxjava.service.analyse;

import org.rxjava.apikit.stream.tool.ApikitApplication;
import org.rxjava.apikit.stream.tool.ApikitContext;
import org.rxjava.apikit.stream.tool.info.ControllerInfo;
import org.rxjava.apikit.stream.tool.model.ApidocGroupModel;
import org.rxjava.apikit.stream.tool.model.ApidocModel;
import org.rxjava.apikit.stream.tool.model.ParamModel;
import org.rxjava.apikit.tool.ApiBuilder;
import org.rxjava.apikit.tool.analyse.impl.ApiAnalyse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
//        //设置Java生成后的文件夹路径
//        String javaOutPath = new File(moduleAbsolutePath, "src/test/java/").getAbsolutePath();
//        //设置Js生成后的文件夹路径
//        String jsOutPath = new File(moduleAbsolutePath, "src/test/js/").getAbsolutePath();
//        //设置Js生成后的文件夹路径
//        String apidocOutPath = new File(moduleAbsolutePath, "src/test/apidoc/").getAbsolutePath();
//
//        //分析
//        ApiBuilder apiBuilder = new ApiBuilder();
//        apiBuilder.build("org.rxjava.service.analyse",javaOutPath);
        runStream(javaSourceDir);
    }

    private void runStream(String javaSourceDir) {
        ApikitContext apikitContext = new ApikitContext();
        apikitContext.setAnalysePackage("org.rxjava.service.analyse");
        apikitContext.setSrcMainJavaPath(javaSourceDir);
        ApikitApplication.builder().build().start()
                .subscriberContext(context -> context.put("apikitContext", apikitContext)).block();
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
