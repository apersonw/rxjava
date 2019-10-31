package org.rxjava.apikit.stream.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author happy 2019/10/27 17:52
 */
@Getter
@Setter
public class ParamInfo extends ClassInfo {
    /**
     * 是否数组
     */
    private boolean array;
    /**
     * 参数字段名
     */
    private String fieldName;
    /**
     * 子参数信息
     * 为null则说明是不再分析的类型
     */
    private List<ParamInfo> childParamInfo;
    /**
     * 泛型中的实际类型列表
     * 非null则说明是泛型
     */
    private Map<String, ParamInfo> actualTypes;
}
