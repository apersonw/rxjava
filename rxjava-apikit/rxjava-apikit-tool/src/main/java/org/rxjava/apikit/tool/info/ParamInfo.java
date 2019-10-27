package org.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy 2019/10/27 17:52
 */
@Getter
@Setter
public class ParamInfo extends ClassBaseInfo{
    /**
     * 原始或包装类型
     */
    private boolean primitiveOrWrapper;
    /**
     * 数组
     */
    private boolean array;
    /**
     * 子参数信息
     */
    private List<ParamInfo> childParamInfo=new ArrayList<>();
}
