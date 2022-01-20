package top.rxjava.apikit.tool.wrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import reactor.core.publisher.Flux;
import top.rxjava.apikit.tool.generator.Context;
import top.rxjava.apikit.tool.generator.NameMaper;
import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.ApiInputClassInfo;
import top.rxjava.apikit.tool.info.ApiMethodInfo;
import top.rxjava.apikit.tool.info.ClassTypeInfo;
import top.rxjava.apikit.tool.utils.CommentUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .map(ClassTypeInfo::getFullName)
                .distinct()
                .sort(Comparator.naturalOrder())
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
        resultTypeString(sb, method, method.getReturnClass());
        sb.append(";");
        return sb.toString();
    }

    public String toJavaTypeString(ClassTypeInfo typeInfo) {
        return toJavaTypeString(typeInfo, true, true, true);
    }

    public String resultData(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        ClassTypeInfo resultType = method.getResultDataType();

        if (method.isFlux()) {
            sb.append("Flux<").append(toJavaTypeString(resultType, true, true)).append(">");
        } else {
            sb.append("Mono<").append(toJavaTypeString(resultType, true, true)).append(">");
        }

        return sb.toString();
    }

    private void resultTypeString(StringBuilder sb, ApiMethodInfo apiMethodInfo, ClassTypeInfo resultType) {
        if (apiMethodInfo.isFlux()) {
            sb.append(" ApiUtils.type(List.class, ").append(toJavaTypeString(resultType, true, false, false)).append(".class");
        } else {
            sb.append(" ApiUtils.type(").append(toJavaTypeString(resultType, true, false, false)).append(".class");
        }
        if (resultType.getTypeArguments().isEmpty()) {
            sb.append(")");
        } else {
            for (ClassTypeInfo typeArgument : resultType.getTypeArguments()) {
                sb.append(",");
                resultTypeString(sb, apiMethodInfo, typeArgument);
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

    public String toResultDataType(ApiMethodInfo method) {
        StringBuilder sb = new StringBuilder();
        if (method.isFlux()) {
            sb.append("List<");
        }
        sb.append(toJavaTypeString(method.getResultDataType()));
        if (method.isFlux()) {
            sb.append(">");
        }
        return sb.toString();
    }

    public JavaApiWrapper(Context context, ApiClassInfo classInfo, String rootPackage, NameMaper apiNameMaper) {
        super(context, classInfo, rootPackage);
        this.apiNameMaper = apiNameMaper;
    }

    public String requestComment(ApiMethodInfo method, String start) {
        StringBuilder sb = new StringBuilder(start);
        sb.append("<div class='http-info'>http 说明")
                .append("<ul>\n");

        sb.append(start).append("<li><b>Uri:</b>").append(method.getUrl()).append("</li>\n");

        Map<String, String> stringStringMap = CommentUtils.toMap(method.getJavaDocInfo());

        List<ApiInputClassInfo> params = method.getParams();
        for (ApiInputClassInfo attributeInfo : params) {
            if (attributeInfo.isPathParam()) {
                String name = attributeInfo.getFieldName();
                String txt = stringStringMap.get(name);
                sb.append(start).append("<li><b>PathVariable:</b> ")
                        .append(
                                StringEscapeUtils.escapeHtml4(
                                        toJavaTypeString(attributeInfo.getClassTypeInfo())
                                )
                        )
                        .append(" ")
                        .append(attributeInfo.getFieldName());

                if (StringUtils.isNotEmpty(txt)) {
                    sb.append(" ");
                    sb.append("<span>");
                    sb.append(txt);
                    sb.append("</span>");
                }
                sb.append("</li>\n");
            } else if (attributeInfo.isValidParam()) {
                sb.append(start).append("<li><b>Form:</b>")
                        .append(
                                StringEscapeUtils.escapeHtml4(
                                        toJavaTypeString(attributeInfo.getClassTypeInfo())
                                )
                        )
                        .append(method.getMethodName())
                        .append("</li>\n");
            }
        }

        String returnType = toJavaTypeString(method.getReturnClass());

        sb.append(start).append("<li><b>Model:</b> ").append("").append(
                StringEscapeUtils.escapeHtml4(returnType)
        ).append("").append("</li>\n");

        if (method.isLogin()) {
            sb.append(start).append("<li>需要登录</li>\n");
        }

        sb.append(start).append("</ul>\n").append(start).append("</div>\n");

        Map<String, ApiInputClassInfo> paramMap = method
                .getParams()
                .stream()
                .collect(Collectors.toMap(ApiInputClassInfo::getFieldName, r -> r));

        if (method.getJavaDocInfo() != null) {
            List<List<String>> param = method.getJavaDocInfo().get("@param");
            if (CollectionUtils.isNotEmpty(param)) {
                param.stream().filter(r -> r.size() > 1).forEach(list -> {
                    ApiInputClassInfo methodParamInfo = paramMap.get(list.get(0));
                    if (methodParamInfo != null) {
                        sb.append(start).append("@param ")
                                .append(list.get(0))
                                .append(" ")
                                .append(list.size() > 1 ? String.join(" ", list.subList(1, list.size())) : "")
                                .append("\n");
                    }
                });
            }
        }

        method.getAllTypes().forEach(typeInfo -> {
            sb.append(start).append("@see ").append(
                    StringEscapeUtils.escapeHtml4(
                            toJavaTypeString(typeInfo)
                    )
            ).append("\n");
        });
        return StringUtils.stripEnd(sb.toString(), null);
    }

}
