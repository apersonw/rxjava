package org.rxjava.apikit.stream.tool.build;

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
                .flatMap(context -> Flux.fromIterable((List<ControllerInfo>) context.get("controllerInfos"))
                        .map(controllerInfo -> {
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
                )
                .thenReturn("hello");
    }
}
