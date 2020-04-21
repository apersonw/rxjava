package org.rxjava.apikit.tool.analyse.impl;

import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import httl.util.CollectionUtils;
import io.github.classgraph.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.apikit.annotation.Ignore;
import org.rxjava.apikit.core.HttpMethodType;
import org.rxjava.apikit.tool.analyse.Analyse;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.ApiClassInfo;
import org.rxjava.apikit.tool.info.ApiInputClassInfo;
import org.rxjava.apikit.tool.info.ApiMethodInfo;
import org.rxjava.apikit.tool.info.ClassTypeInfo;
import org.rxjava.apikit.tool.utils.JdtClassWrapper;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author happy
 * 控制器类分析器
 */
public class ControllerAnalyse implements Analyse {
    private Context context;

    public static ControllerAnalyse create() {
        return new ControllerAnalyse();
    }

    /**
     * 开始扫描根包下的类文件
     */
    @Override
    public void analyse(Context context) {
        this.context = context;
        try (ScanResult scanResult =
                     new ClassGraph()
                             .enableAllInfo()
                             .whitelistPackages(context.getRootPackage())
                             .addClassLoader(ControllerAnalyse.class.getClassLoader())
                             .scan()) {
            scanResult.getAllClasses()
                    .filter(classInfo -> {
                        //检查类是否处于root包下
                        String classPackageName = classInfo.getName();
                        if (!classPackageName.startsWith(context.getRootPackage())) {
                            return false;
                        }

                        //检查类上是否有Controller注解
                        return classInfo.getAnnotationInfo(Controller.class.getName()) != null;
                    })
                    .forEach(this::analyseClass);
        }
    }

    /**
     * 分析Controller类信息
     */
    private void analyseClass(ClassInfo classInfo) {
        //分析类下的Api信息
        ApiClassInfo apiClassInfo = new ApiClassInfo();
        //Api名称
        apiClassInfo.setClassName(classInfo.getSimpleName());
        //包名
        apiClassInfo.setPackageName(classInfo.getPackageName());

        //从源码类获取注释信息
        JdtClassWrapper jdtClassWrapper = new JdtClassWrapper(this.context.getJavaFilePath(), classInfo.loadClass());
        apiClassInfo.setJavaDocInfo(jdtClassWrapper.getClassComment());

        //使用spring的注解帮助类可以获取到更多的信息
        RequestMapping requestMappingAnnotation = AnnotationUtils.getAnnotation(classInfo.loadClass(), RequestMapping.class);
        String classMappingPath = (requestMappingAnnotation != null && ArrayUtils.isNotEmpty(requestMappingAnnotation.path()))
                ? requestMappingAnnotation.path()[0]
                : "";

        MethodInfoList methodInfoList = classInfo.getMethodInfo();
        List<ApiMethodInfo> apiMethodInfos = methodInfoList
                .stream()
                .filter(methodInfo -> {
                    Method method = methodInfo.loadClassAndGetMethod();
                    RequestMapping methodInfoAnnotationInfo = AnnotationUtils.getAnnotation(method, RequestMapping.class);
                    Ignore ignoreAnnotation = AnnotationUtils.getAnnotation(method, Ignore.class);
                    return null != methodInfoAnnotationInfo && null == ignoreAnnotation;
                })
                .map(methodInfo -> this.analyseMethod(methodInfo, classMappingPath, jdtClassWrapper))
                .sorted((m1, m2) -> StringUtils.compare(m1.getMethodName(), m2.getMethodName()))
                .collect(Collectors.toList());
        Objects.requireNonNull(apiMethodInfos).forEach(apiClassInfo::addApiMethod);
        context.addApi(apiClassInfo);
    }

    /**
     * 分析Controller类方法
     */
    private ApiMethodInfo analyseMethod(MethodInfo methodInfo, String parentPath, JdtClassWrapper jdtClassWrapper) {
        Method method = methodInfo.loadClassAndGetMethod();

        AnnotationAttributes annotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, RequestMapping.class);
        RequestMethod[] requestMethods = (RequestMethod[]) Objects.requireNonNull(annotationAttributes).get("method");

        ApiMethodInfo apiMethodInfo = new ApiMethodInfo();
        apiMethodInfo.setHttpMethodTypes(toMethodTypes(requestMethods));
        apiMethodInfo.setMethodName(methodInfo.getName());
        apiMethodInfo.setJavaDocInfo(jdtClassWrapper.getMethodComment(methodInfo.getName()));

        //访问路径
        String[] pathArray = Objects.requireNonNull(annotationAttributes).getStringArray("path");
        String accessPath = toPath(parentPath, pathArray);
        apiMethodInfo.setUrl(accessPath);

        analyseInputClass(method, apiMethodInfo);

        Type type = method.getGenericReturnType();
        analyseReturnClass(type, apiMethodInfo);

        return apiMethodInfo;
    }

    /**
     * 分析Controller类方法入参信息
     */
    private void analyseInputClass(Method method, ApiMethodInfo apiMethodInfo) {
        Parameter[] parameters = method.getParameters();
        Paranamer info = new CachingParanamer(new AnnotationParanamer(new BytecodeReadingParanamer()));
        String[] parameterNames = info.lookupParameterNames(method);

        for (int i = 0, parametersLength = parameters.length; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isVarArgs()) {
                throw new RuntimeException("暂时不支持可变参数 varargs");
            }
            String paramName = parameterNames[i];
            if (paramName == null) {
                throw new RuntimeException("请修改编译选项，保留方法参数名称");
            }

            Type pType = parameter.getParameterizedType();
            ApiInputClassInfo apiInputClassInfo = new ApiInputClassInfo(paramName, ClassTypeInfo.form(pType));

            //检查参数是否路径参数
            AnnotationAttributes pathVarAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(parameter, PathVariable.class);
            if (MapUtils.isNotEmpty(pathVarAnnotationAttributes)) {
                apiInputClassInfo.setPathVariable(true);
            }

            AnnotationAttributes requestParamAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(parameter, RequestParam.class);
            if (MapUtils.isNotEmpty(requestParamAnnotationAttributes)) {
                apiInputClassInfo.setRequestParam(true);
            }

            //检查是否表单校验参数
            Valid validAnnotation = AnnotationUtils.getAnnotation(parameter, Valid.class);
            if (validAnnotation != null) {
                apiInputClassInfo.setFormParam(true);
            }
            //过滤掉可选参数
            if (!apiInputClassInfo.isFormParam() && !apiInputClassInfo.isPathVariable()&&!apiInputClassInfo.isRequestParam()) {
                continue;
            }

            if (apiInputClassInfo.isFormParam() && apiInputClassInfo.isPathVariable()) {
                throw new RuntimeException("参数不能同时是路径参数和form" + apiInputClassInfo);
            }
            if (apiInputClassInfo.isFormParam()) {
                if (apiInputClassInfo.getTypeInfo().isArray()) {
                    throw new RuntimeException("表单对象不支持数组!" + apiInputClassInfo);
                }
            }
            if (apiInputClassInfo.getTypeInfo().getType() == ClassTypeInfo.Type.VOID) {
                throw new RuntimeException("void 类型只能用于返回值");
            }
            apiMethodInfo.addParam(apiInputClassInfo);
        }
    }

    /**
     * 分析Controller类方法出参信息
     */
    private void analyseReturnClass(Type type, ApiMethodInfo apiMethodInfo) {
        if (type == null) {
            throw new RuntimeException("返回类型不能为空!" + apiMethodInfo);
        }

        ClassTypeInfo resultType = ClassTypeInfo.form(type);

        /*
         * 最后的返回类型
         */
        Class<?> cls = null;
        try {
            cls = resultType.toClass();
        } catch (ClassNotFoundException ignored) {
        }
        if (cls != null
                && (Flux.class.isAssignableFrom(cls)
                || Mono.class.isAssignableFrom(cls))) {
            if (CollectionUtils.isEmpty(resultType.getTypeArguments())) {
                throw new RuntimeException("类型不存在！!" + resultType);
            }
            if (resultType.getTypeArguments().size() != 1) {
                throw new RuntimeException("返回参数的类型变量数只能是1！!" + resultType);
            }
            boolean isSingle = Mono.class.isAssignableFrom(cls);
            ClassTypeInfo realResultType = resultType.getTypeArguments().get(0);

            if (isSingle) {
                apiMethodInfo.setReturnClass(realResultType);

                apiMethodInfo.setResultDataType(realResultType);
            } else {
                ClassTypeInfo realResultTypeArray = realResultType.clone();
                realResultTypeArray.setArray(true);

                apiMethodInfo.setReturnClass(realResultTypeArray);


                apiMethodInfo.setResultDataType(realResultTypeArray);
            }
        } else {
            apiMethodInfo.setReturnClass(resultType);
            apiMethodInfo.setResultDataType(resultType);
        }
    }

    /**
     * 组装类+方法路径
     */
    private String toPath(String parentPath, String[] pathArray) {
        String path = (ArrayUtils.isNotEmpty(pathArray))
                ? pathArray[0]
                : "";

        return Stream.of(parentPath, path)
                .flatMap(p -> Stream.of(p.split("/")))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining("/", "", ""));
    }

    /**
     * 获取请求Http请求方法数组
     */
    private HttpMethodType[] toMethodTypes(RequestMethod[] requestMapping) {
        return Objects.requireNonNull(Flux
                .just(requestMapping)
                .map(m -> HttpMethodType.valueOf(m.name()))
                .collectList()
                .filter(r -> !r.isEmpty())
                .switchIfEmpty(Mono.just(Arrays.asList(HttpMethodType.values())))
                .block())
                .toArray(new HttpMethodType[]{});
    }
}
