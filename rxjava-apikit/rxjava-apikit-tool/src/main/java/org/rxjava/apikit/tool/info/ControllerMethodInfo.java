package org.rxjava.apikit.tool.info;

import org.rxjava.apikit.core.HttpMethodType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy 2019/10/26 22:32
 * 控制器方法信息
 */
@Data
public class ControllerMethodInfo {
    /**
     * 方法名
     */
    private String methodName;
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
    private List<InputParamInfo> inputParamInfos = new ArrayList<>();
    /**
     * 输出参数信息
     */
    private ParamInfo returnParamInfo;

    public void addInputParams(InputParamInfo inputParamInfo) {
        inputParamInfos.add(inputParamInfo);
    }
}
