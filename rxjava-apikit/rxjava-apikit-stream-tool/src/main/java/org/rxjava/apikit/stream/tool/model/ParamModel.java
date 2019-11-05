package org.rxjava.apikit.stream.tool.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 参数
 */
@Getter
@Setter
public class ParamModel {
    /**
     * 节点
     */
    private String field;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 子类型
     */
    private List<ParamModel> childParamModel;
    /**
     * 可为空
     */
    private boolean notEmpty;
    /**
     * 说明描述
     */
    private String desc;
}
