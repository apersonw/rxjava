package top.rxjava.apikit.tool.wrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import top.rxjava.apikit.tool.generator.Context;
import top.rxjava.apikit.tool.generator.NameMaper;
import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.ApiMethodInfo;
import top.rxjava.apikit.tool.info.ApiInputClassInfo;
import top.rxjava.apikit.tool.info.ClassTypeInfo;
import top.rxjava.apikit.tool.utils.CommentUtils;
import top.rxjava.apikit.tool.utils.NameUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author happy 2019-05-09 23:03
 */
@Getter
@Setter
@ToString
public class JavaScriptApiWrapper extends JavaScriptWrapper<ApiClassInfo> {
    private String packageName;

    private NameMaper nameMaper;

    public JavaScriptApiWrapper(Context context, ApiClassInfo classInfo, String rootPackage, NameMaper nameMaper, String serviceId) {
        super(context, classInfo, rootPackage);
        this.nameMaper = nameMaper;
        this.setServiceId(serviceId);
    }

    private int getMyLevel() {
        String classInfoPackageName = getDistFolder();
        if (StringUtils.isEmpty(classInfoPackageName)) {
            return 0;
        } else {
            return classInfoPackageName.split("\\.").length + 1;
        }
    }

    public String params(ApiMethodInfo method) {
        return params(method, false);
    }

    public String params(ApiMethodInfo method, boolean isType) {
        StringBuilder sb = new StringBuilder();
        List<ApiInputClassInfo> params = method.getParams();
        for (ApiInputClassInfo apiInputClassInfo : params) {
            if (apiInputClassInfo.isValidParam() || apiInputClassInfo.isPathParam() || apiInputClassInfo.isRequestParam()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(apiInputClassInfo.getFieldName());
                if (isType) {
                    sb.append(":");
                    sb.append(toTypeString(apiInputClassInfo.getClassTypeInfo()));
                }
            }
        }
        return sb.toString();
    }

    public String paramType(ApiInputClassInfo apiInputClassInfo) {
        return toTypeString(apiInputClassInfo.getClassTypeInfo());
    }

    public String fieldName() {
        return NameUtils.toFieldName(getDistClassName());
    }

    @Override
    public String getDistClassName() {
        return nameMaper.apply(super.getDistClassName());
    }

    public String imports() {
        return imports(true);
    }

    public String imports(boolean isModel) {
        String imports = isModel ? getMethodImports(false) : "";
        return imports + "import { AbstractApi, Method } from 'rxjava-api-core'\n";
    }

    public String es2015imports() {
        return getMethodImports(true);
    }

    public String getMethodImports(boolean isEs2015) {
        StringBuilder sb = new StringBuilder();

        int myLevel = getMyLevel();

        Flux
                .fromIterable(classInfo.getApiMethodList())
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
                .filter(typeInfo -> typeInfo.getType().equals(ClassTypeInfo.TypeEnum.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .map(ClassTypeInfo::getFullName)
                .distinct()
                .sort(Comparator.naturalOrder())
                .filter(fullName -> context.getParamWrapper(fullName) != null)
                .map(fullName -> context.getParamWrapper(fullName))
                .filter(w -> !w.getDistFolder().equals(getDistFolder()))
                .doOnNext(r -> {
                    String name = r.getDistClassName();
                    if (isEs2015) {
//                        var _TestForm = _interopRequireDefault(require("./form/TestForm"));
                        sb.append("var _")
                                .append(name)
                                .append(" = _interopRequireDefault(require('./")
                                .append(StringUtils.repeat("../", myLevel))
                                .append(r.getDistFolder().replace(".", "/")).append('/').append(name).append("'))\n");
                    } else {
                        sb.append("import ")
                                .append(name)
                                .append(" from './")
                                .append(StringUtils.repeat("../", myLevel))
                                .append(r.getDistFolder().replace(".", "/")).append('/').append(name).append("'\n");
                    }
                })
                .collectList()
                .block();

        return sb.toString();
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
                                        toTypeString(attributeInfo.getClassTypeInfo())
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
                                        toTypeString(attributeInfo.getClassTypeInfo())
                                )
                        )
                        .append("")
                        .append(method.getMethodName())
                        .append("</li>\n");
            }
        }

        String returnType = toTypeString(method.getReturnClass());

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
                            toTypeString(typeInfo, false)
                    )
            ).append("\n");
        });
        return StringUtils.stripEnd(sb.toString(), null);
    }

    public String resultTypeString(ApiMethodInfo method) {
        String returnType = toTypeString(method.getReturnClass());
        return StringEscapeUtils.escapeHtml4(returnType);
    }

}
