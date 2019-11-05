package org.rxjava.apikit.stream.tool.info;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.apikit.core.HttpMethodType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author happy 2019/10/26 22:32
 * 控制器方法信息
 */
@Getter
@Setter
public class MethodInfo {
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法注释名
     */
    private String commentName;
    /**
     * 方法注释描述
     */
    private String commentDesc;
    /**
     * 请求url
     */
    private String requestUrl;
    /**
     * http请求方法数组
     */
    private HttpMethodType[] httpMethodTypes;
    /**
     * 输入参数信息列表
     */
    private List<ParamInfo> inputParamInfos = new ArrayList<>();
    /**
     * 输入字段参数注释信息
     */
    private Map<String, FieldCommentInfo> inputFieldCommentInfoMap;
    /**
     * 输出参数信息
     */
    private ParamInfo returnParamInfo;
    /**
     * 返回值注释信息
     */
    private String returnComment;

    public void addInputParams(ParamInfo inputParamInfo) {
        inputParamInfos.add(inputParamInfo);
    }
}
