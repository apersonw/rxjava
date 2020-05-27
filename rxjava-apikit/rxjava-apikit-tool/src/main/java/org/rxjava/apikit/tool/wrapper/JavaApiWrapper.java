package org.rxjava.apikit.tool.wrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.generator.NameMaper;
import org.rxjava.apikit.tool.info.ApiClassInfo;
import org.rxjava.apikit.tool.info.ApiMethodInfo;
import org.rxjava.apikit.tool.info.ApiInputClassInfo;
import org.rxjava.apikit.tool.info.ClassTypeInfo;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author happy
 */
@Setter
@Getter
@ToString
@Slf4j
public class JavaApiWrapper extends JavaWrapper<ApiClassInfo> {

    /**
     * Api名称映射
     */
    private NameMaper apiNameMaper;

    /**
     * 导入依赖类
     */
    public String imports() {
        StringBuilder sb = new StringBuilder();
        Flux.fromIterable(classInfo.getApiMethodList())
                .flatMapIterable(methodInfo -> {
                    List<ClassTypeInfo> types = new ArrayList<>();
                    types.add(methodInfo.getResultDataType());
                    methodInfo.getParams().forEach(p -> types.add(p.getClassTypeInfo()));
                    return types;
                })
                .flatMapIterable(type -> {
                    List<ClassTypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    return types;
                })
                .filter(classTypeInfo -> classTypeInfo.getType().equals(ClassTypeInfo.TypeEnum.OTHER))
                // .filter(typeInfo -> !typeInfo.isCollection())
                // .filter(typeInfo -> !typeInfo.isGeneric())
                // .filter(ClassTypeInfo::isObjectId)
                .map(ClassTypeInfo::getFullName)
                .distinct()
                .sort(Comparator.naturalOrder())
                .map(a -> {
                    log.info("JavaApiWrapper->imports:{}", a);
                    return a;
                })
                .filter(fullName -> context.getParamWrapper(fullName) != null)
                .map(fullName -> context.getParamWrapper(fullName))
                .filter(w -> !w.getFullDistPackage().equals(getFullDistPackage()))
                .doOnNext(r -> sb.append("import ").append(r.getFullDistPackage()).append(".").append(r.getDistClassName()).append(";\n"))
                .collectList()
                .block();

        return sb.toString();
    }

    /**
     * 修改api名称映射
     */
    @Override
    public String getDistClassName() {
        return apiNameMaper.apply(super.getDistClassName());
    }

    /**
     * 结果类型字符串
     */
    public String resultTypeString(ApiMethodInfo method, String start) {
        StringBuilder sb = new StringBuilder(start);
        sb.append("private static final ApiType _").append(method.getIndex()).append("Type = ");
        resultTypeString(sb, method.getReturnClass());
        sb.append(";");
        return sb.toString();
    }

    public String resultData(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        ClassTypeInfo resultType = method.getResultDataType();
        sb.append(toJavaTypeString(resultType, true, true));
        return sb.toString();
    }

    private void resultTypeString(StringBuilder sb, ClassTypeInfo resultType) {
        if (resultType.isArray()) {
            sb.append(" ApiUtils.type(java.util.ArrayList.class, ").append(toJavaTypeString(resultType, true, false, false)).append(".class");
        } else {
            sb.append(" ApiUtils.type(").append(toJavaTypeString(resultType, true, false, false)).append(".class");
        }
        if (resultType.getTypeArguments().isEmpty()) {
            sb.append(")");
        } else {
            for (ClassTypeInfo typeArgument : resultType.getTypeArguments()) {
                sb.append(",");
                resultTypeString(sb, typeArgument);
            }
            sb.append(")");
        }
    }

    /**
     * 获取api方法参数字符串
     */
    public String params(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        List<ApiInputClassInfo> params = method.getParams();
        for (int i = 0; i < params.size(); i++) {
            ApiInputClassInfo apiInputClassInfo = params.get(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(toJavaTypeString(apiInputClassInfo.getClassTypeInfo(), false, true));
            sb.append(' ');
            sb.append(apiInputClassInfo.getFieldName());
        }
        return sb.toString();
    }

    public JavaApiWrapper(Context context, ApiClassInfo classInfo, String rootPackage, NameMaper apiNameMaper) {
        super(context, classInfo, rootPackage);
        this.apiNameMaper = apiNameMaper;
    }

}
