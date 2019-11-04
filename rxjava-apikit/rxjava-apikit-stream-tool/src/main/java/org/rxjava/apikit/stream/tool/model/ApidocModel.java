package org.rxjava.apikit.stream.tool.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 一个apidoc model就是一个控制器
 */
@Getter
@Setter
public class ApidocModel {
    /**
     * 所属方法名
     */
    private String methodName;
    /**
     * 请求url
     */
    private String url;
    /**
     * 使用说明
     */
    private String desc;
    /**
     * 输入参数
     */
    private List<ParamModel> inputs;
    /**
     * 输出参数
     */
    private List<ParamModel> outputs;
}
