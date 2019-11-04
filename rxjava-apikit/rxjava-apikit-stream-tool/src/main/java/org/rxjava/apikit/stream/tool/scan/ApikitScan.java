package org.rxjava.apikit.stream.tool.scan;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.apikit.annotation.Ignore;
import org.rxjava.apikit.core.HttpMethodType;
import org.rxjava.apikit.stream.tool.ApikitContext;
import org.rxjava.apikit.stream.tool.info.ControllerInfo;
import org.rxjava.apikit.stream.tool.info.InputParamInfo;
import org.rxjava.apikit.stream.tool.info.MethodInfo;
import org.rxjava.apikit.stream.tool.info.ParamInfo;
import org.rxjava.apikit.stream.tool.type.ApiType;
import org.rxjava.apikit.stream.tool.utils.ClassAnalyseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    /**
     * 分析类信息
     *
     * @param cls 待分析的类
     */
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

    /**
     * 分析方法信息
     *
     * @param method           待分析的方法
     * @param classMappingPath 类上的映射路径
     * @return 方法分析结果
     */
    private MethodInfo analyseMethod(Method method, String classMappingPath) {
        AnnotationAttributes annotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, RequestMapping.class);
        String[] methodPathArray = Objects.requireNonNull(annotationAttributes).getStringArray("path");
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setMethodName(method.getName());
        String requestUrl = toRequestUrl(classMappingPath, methodPathArray);
        methodInfo.setRequestUrl(requestUrl);

        RequestMethod[] requestMethods = (RequestMethod[]) annotationAttributes.get("method");
        HttpMethodType[] httpMethodTypes = toHttpMethodTypes(requestMethods);
        methodInfo.setHttpMethodTypes(httpMethodTypes);

        analyseInputParam(method, methodInfo);

//        analyseOutputParam(methodInfo, method);
        return methodInfo;
    }

    /**
     * 分析Controller类方法入参信息
     * @param method 待分析方法
     * @param methodInfo
     */
    /**
     * 三、分析输入参数
     */
    private void analyseInputParam(Method method, MethodInfo methodInfo) {
        Parameter[] parameters = method.getParameters();
        ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isVarArgs()) {
                throw new RuntimeException("不支持varArgs参数");
            }

            Type parameterizedType = parameter.getParameterizedType();

            ParamInfo paramInfo = ClassAnalyseUtils.analyse(parameterizedType);
            InputParamInfo inputParamInfo = new InputParamInfo();
            BeanUtils.copyProperties(paramInfo, inputParamInfo);
            //设置参数名
            String parameterName = parameterNames[i];
            inputParamInfo.setFieldName(parameterName);

            //是否有路径注解
            AnnotationAttributes pathVarAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(parameter, PathVariable.class);
            if (MapUtils.isNotEmpty(pathVarAnnotationAttributes)) {
                inputParamInfo.setPathVariable(true);
            }
            //是否有验证注解
            Valid validAnnotation = AnnotationUtils.getAnnotation(parameter, Valid.class);
            if (validAnnotation != null) {
                inputParamInfo.setValid(true);
            }
            if (inputParamInfo.isValid() && inputParamInfo.isPathVariable()) {
                throw new RuntimeException("同一参数不能同时有路径注解和验证注解");
            }

            methodInfo.addInputParams(inputParamInfo);
        }
    }

    /**
     * 四、分析输出参数
     */
    private void analyseOutputParam(MethodInfo methodInfo, Method method) {
        Type genericReturnType = method.getGenericReturnType();
        ParamInfo paramInfo = ClassAnalyseUtils.analyse(genericReturnType);
        methodInfo.setReturnParamInfo(paramInfo);
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
