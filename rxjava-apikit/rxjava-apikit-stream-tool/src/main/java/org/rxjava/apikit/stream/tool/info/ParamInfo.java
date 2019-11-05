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
     * 是否有@PathVariable注解
     */
    private boolean pathVariable;
    /**
     * 是否有@Valid注解
     */
    private boolean valid;
    /**
     * 是否有@RequestParam注解
     */
    private boolean requestParam;
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
    private List<ParamInfo> childParamInfos;
    /**
     * 泛型中的实际类型列表
     * 非null则说明是泛型
     */
    private Map<String, ParamInfo> actualTypes;
}
