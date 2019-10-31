package org.rxjava.apikit.stream.tool.scan;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.apikit.annotation.Ignore;
import org.rxjava.apikit.core.HttpMethodType;
import org.rxjava.apikit.stream.tool.ApikitContext;
import org.rxjava.apikit.stream.tool.info.ControllerInfo;
import org.rxjava.apikit.stream.tool.info.MethodInfo;
import org.rxjava.apikit.stream.tool.type.ApiType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * api工具扫描器
 */
public class ApikitScan {
    private String analysePackage;
    /**
     * 控制器信息列表
     */
    @Getter
    private List<ControllerInfo> controllerInfoList = new ArrayList<>();

    /**
     * 扫描待分析包下的类并开始分析
     */
    public Flux<ControllerInfo> scan() {
        return Mono.subscriberContext()
                .map(context -> {
                    ApikitContext apikitContext = context.get("apikitContext");
                    String analysePackage = apikitContext.getAnalysePackage();
                    this.analysePackage = analysePackage;
                    FastClasspathScanner scanner = new FastClasspathScanner(analysePackage);
                    scanner.addClassLoader(ApikitScan.class.getClassLoader());
                    scanner.matchAllClasses(this::analyseClass);
                    scanner.scan();
                    return controllerInfoList;
                })
                .flatMapMany(controllerInfos -> Flux.fromIterable(controllerInfos));
    }

    private void analyseClass(Class cls) {
        //仅分析指定包下类
        String classPackageName = cls.getPackage().getName();
        if (!classPackageName.startsWith(analysePackage)) {
            return;
        }
        //检查类是否控制器类
        Controller controllerAnnotation = AnnotationUtils.getAnnotation(cls, Controller.class);
        if (controllerAnnotation == null) {
            return;
        }

        //开始分析控制器
        ControllerInfo controllerInfo = new ControllerInfo();
        controllerInfo.setName(cls.getName());
        controllerInfo.setSimpleName(cls.getSimpleName());
        controllerInfo.setPackageName(classPackageName);
        //获取控制器类mapping路径
        RequestMapping requestMappingAnnotation = AnnotationUtils.getAnnotation(cls, RequestMapping.class);
        String classMappingPath = (requestMappingAnnotation != null && ArrayUtils.isNotEmpty(requestMappingAnnotation.path()))
                ? requestMappingAnnotation.path()[0]
                : "";
        String purePath = classMappingPath.replace("/", "");
        //默认空的话为客户端api
        if (purePath.isEmpty()) {
            purePath = "person";
        }
        ApiType apiType = ApiType.valueOf(purePath);
        controllerInfo.setApiType(apiType);

        //获取控制器方法列表
        List<MethodInfo> controllerMethodInfos = Arrays.stream(cls.getMethods())
                .filter(method -> null != AnnotationUtils.getAnnotation(method, RequestMapping.class) && null == AnnotationUtils.getAnnotation(method, Ignore.class))
                .map(method -> this.analyseMethod(method, classMappingPath))
                //根据方法名称排序
                .sorted((m1, m2) -> StringUtils.compare(m1.getMethodName(), m2.getMethodName()))
                .collect(Collectors.toList());
        controllerInfoList.add(controllerInfo);
    }

    private MethodInfo analyseMethod(Method method, String classMappingPath) {
        AnnotationAttributes annotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, RequestMapping.class);
        String[] methodPathArray = Objects.requireNonNull(annotationAttributes).getStringArray("path");
        MethodInfo controllerMethodInfo = new MethodInfo();
        controllerMethodInfo.setMethodName(method.getName());
        String requestUrl = toRequestUrl(classMappingPath, methodPathArray);
        controllerMethodInfo.setRequestUrl(requestUrl);

        RequestMethod[] requestMethods = (RequestMethod[]) annotationAttributes.get("method");
        HttpMethodType[] httpMethodTypes = toHttpMethodTypes(requestMethods);
        controllerMethodInfo.setHttpMethodTypes(httpMethodTypes);

//        analyseInputParam(controllerMethodInfo, method);

//        analyseOutputParam(controllerMethodInfo, method);
        return controllerMethodInfo;
    }

    /**
     * 转为方法请求url
     */
    private String toRequestUrl(String classMappingPath, String[] methodPathArray) {
        String path = (ArrayUtils.isNotEmpty(methodPathArray))
                ? methodPathArray[0]
                : "";

        return Stream.of(classMappingPath, path)
                .flatMap(p -> Arrays.stream(p.split("/")))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining("/", "", ""));
    }

    /**
     * 获取请求Http请求方法数组
     */
    private HttpMethodType[] toHttpMethodTypes(RequestMethod[] requestMethods) {
        HttpMethodType[] httpMethodTypes = Arrays.stream(requestMethods)
                .map(requestMethod -> HttpMethodType.valueOf(requestMethod.name()))
                .collect(Collectors.toList())
                .toArray(new HttpMethodType[]{});
        return httpMethodTypes.length == 0 ? HttpMethodType.values() : httpMethodTypes;
    }

}
