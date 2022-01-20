package top.rxjava.apikit.tool.info;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import top.rxjava.apikit.core.HttpMethodType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author happy
 * Api方法信息
 */
@Data
public class ApiMethodInfo {
    /**
     * 索引号
     */
    private int index;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法请求url
     */
    private String url;
    /**
     * 返回值是否Flux
     */
    private boolean flux = false;
    /**
     * Http请求方法类型,默认为Get
     */
    private HttpMethodType[] httpMethodTypes = new HttpMethodType[]{HttpMethodType.GET};
    /**
     * 原始类型
     */
    private ClassTypeInfo returnClass;
    /**
     * 返回值的类型
     */
    private ClassTypeInfo resultDataType;
    /**
     * 所有的参数
     */
    private List<ApiInputClassInfo> params = new ArrayList<>();
    /**
     * 路径参数列表
     */
    private List<ApiInputClassInfo> pathParams = new ArrayList<>();
    /**
     * 验证参数列表
     */
    private List<ApiInputClassInfo> validParams = new ArrayList<>();
    /**
     * 请求参数列表
     */
    private List<ApiInputClassInfo> requestParams = new ArrayList<>();
    /**
     * json参数列表
     */
    private List<ApiInputClassInfo> jsonParams = new ArrayList<>();
    /**
     * requestPart参数列表
     */
    private List<ApiInputClassInfo> requestPartParams = new ArrayList<>();
    /**
     * java注释信息
     */
    private JavaDocInfo javaDocInfo;
    /**
     * 是否需要登陆
     */
    private boolean login = true;

    public HttpMethodType getType() {
        return httpMethodTypes.length > 0 ? httpMethodTypes[0] : null;
    }

    public void addParam(ApiInputClassInfo param) {
        params.add(param);
        if (param.isPathParam()) {
            pathParams.add(param);
        } else if (param.isJsonParam()) {
            if (jsonParams.size() > 0) {
                throw new RuntimeException("Spring不支持绑定多个json参数,请检查方法:" + methodName);
            }
            jsonParams.add(param);
        } else if (param.isRequestParam()) {
            requestParams.add(param);
        } else if (param.isValidParam()) {
            validParams.add(param);
        } else if (param.isRequestPartParam()) {
            requestPartParams.add(param);
        }
    }

    public String getMethodCaseName() {
        char[] methodName = getMethodName().toCharArray();
        char chars = methodName[0];
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        methodName[0] = chars;
        return String.valueOf(methodName);
    }

    protected void findTypes(ClassTypeInfo type, List<ClassTypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }

    public List<ClassTypeInfo> getAllTypes() {
        return Stream.of(this)
                .flatMap(m -> {
                    List<ClassTypeInfo> types = new ArrayList<>();
                    types.add(m.getResultDataType());
                    m.getParams().forEach(p -> types.add(p.getClassTypeInfo()));
                    return types.stream();
                })
                .flatMap(type -> {
                    List<ClassTypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    return types.stream();
                })
                .filter(typeInfo -> typeInfo.getType().equals(ClassTypeInfo.TypeEnum.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .distinct()
                .collect(Collectors.toList());
    }
}
