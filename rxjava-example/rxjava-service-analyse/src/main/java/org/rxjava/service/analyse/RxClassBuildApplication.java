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
//        //模块绝对路径
//        String moduleAbsolutePath = getModuleAbsolutePath("rxjava-service-analyse");
//        //java源码文件夹
//        String javaSourceDir = new File(moduleAbsolutePath, "src/main/java/").getAbsolutePath();
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
        runStream();
    }

    public void runStream(String... args) {
        ApikitContext apikitContext = new ApikitContext();
        apikitContext.setAnalysePackage("org.rxjava.service.analyse");
        List<ControllerInfo> controllerInfoList = ApikitApplication.builder().build().start().collectList()
                .subscriberContext(context -> context.put("apikitContext", apikitContext)).block();

        List<ApidocGroupModel> apidocGroupModels = new ArrayList<>();
        controllerInfoList.forEach(controllerInfo -> {
            ApidocGroupModel apidocGroupModel = new ApidocGroupModel();
            apidocGroupModel.setGroupName(controllerInfo.getSimpleName());
            List<ApidocModel> apidocModels = new ArrayList<>();
            controllerInfo.getMethodInfos().forEach(methodInfo -> {
                ApidocModel apidocModel = new ApidocModel();
                apidocModel.setMethodName(methodInfo.getMethodName());

                List<ParamModel> inputParamModels = methodInfo.getInputParamInfos().stream().map(inputParamInfo -> {
                    ParamModel paramModel = new ParamModel();
                    paramModel.setField(inputParamInfo.getFieldName());
                    paramModel.setType(inputParamInfo.getSimpleName());
                    if (inputParamInfo.isPathVariable() || inputParamInfo.isValid()) {
                        paramModel.setNotEmpty(true);
                    }
                    return paramModel;
                }).collect(Collectors.toList());

                apidocModel.setInputs(inputParamModels);
                apidocModels.add(apidocModel);
            });
            apidocGroupModel.setApidocModels(apidocModels);
            apidocGroupModels.add(apidocGroupModel);
        });

        System.out.println(apidocGroupModels);
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
