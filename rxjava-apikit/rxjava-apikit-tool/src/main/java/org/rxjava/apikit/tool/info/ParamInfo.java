package org.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author happy 2019/10/27 17:52
 */
@Getter
@Setter
public class ParamInfo extends ClassBaseInfo {
    /**
     * 参数字段名
     */
    private String fieldName;
    /**
     * 基本类型
     */
    private boolean baseType;
    /**
     * 子参数信息
     */
    private List<ParamInfo> childParamInfo;
    /**
     * 泛型中的实际类型列表
     */
    private Map<String, ParamInfo> actualTypes;
}
