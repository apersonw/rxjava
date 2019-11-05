package org.rxjava.apikit.stream.tool.build;

import org.apache.commons.collections4.CollectionUtils;
import org.rxjava.apikit.stream.tool.info.ControllerInfo;
import org.rxjava.apikit.stream.tool.info.ParamInfo;
import org.rxjava.apikit.stream.tool.model.ApidocGroupModel;
import org.rxjava.apikit.stream.tool.model.ApidocModel;
import org.rxjava.apikit.stream.tool.model.ParamModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Api文档build
 */
public class ApidocBuild {
    public Mono<String> build() {
        return Mono.subscriberContext()
                .flatMapMany(context -> {
                    List<ControllerInfo> controllerInfos = context.get("controllerInfos");
                    return Flux.fromIterable(controllerInfos);
                })
                .map(controllerInfo -> {
                    ApidocGroupModel apidocGroupModel = new ApidocGroupModel();
                    apidocGroupModel.setGroupName(controllerInfo.getSimpleName());
                    List<ApidocModel> apidocModels = new ArrayList<>();
                    controllerInfo.getMethodInfos().forEach(methodInfo -> {
                        ApidocModel apidocModel = new ApidocModel();
                        apidocModel.setMethodName(methodInfo.getMethodName());
                        apidocModel.setUrl(methodInfo.getRequestUrl());

                        List<ParamModel> inputParamModels = toParamModelList(methodInfo.getInputParamInfos());

                        apidocModel.setInputs(inputParamModels);

                        ParamInfo returnParamInfo = methodInfo.getReturnParamInfo();
                        if (Objects.nonNull(returnParamInfo)) {
                            ParamModel outputParamModel = new ParamModel();
                            outputParamModel.setName(returnParamInfo.getFieldName());
                            outputParamModel.setType(returnParamInfo.getSimpleName());
                            outputParamModel.setNotEmpty(true);
                            List<ParamModel> outputParamModels = new ArrayList<>();
                            outputParamModels.add(outputParamModel);
                            apidocModel.setOutputs(outputParamModels);
                        }
                        apidocModels.add(apidocModel);
                    });
                    apidocGroupModel.setApidocModels(apidocModels);
                    return apidocGroupModel;
                })
                .collectList()
                .thenReturn("hello");
    }

    /**
     * 转为参数列表
     */
    private List<ParamModel> toParamModelList(List<ParamInfo> paramInfos) {
        return paramInfos.stream()
                .map(paramInfo -> {
                    ParamModel paramModel = new ParamModel();
                    paramModel.setField(paramInfo.getFieldName());
                    paramModel.setType(paramInfo.getSimpleName());
                    if (paramInfo.isPathVariable() || paramInfo.isValid() || paramInfo.isRequestParam()) {
                        paramModel.setNotEmpty(true);
                    }
                    List<ParamInfo> childParamInfos = paramInfo.getChildParamInfos();
                    if (CollectionUtils.isNotEmpty(childParamInfos)) {
                        paramModel.setChildParamModel(toParamModelList(childParamInfos));
                    }
                    return paramModel;
                })
                .collect(Collectors.toList());
    }
}
