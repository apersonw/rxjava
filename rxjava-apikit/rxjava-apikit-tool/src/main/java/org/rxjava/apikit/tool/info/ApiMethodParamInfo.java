package org.rxjava.apikit.tool.info;


import lombok.Getter;
import lombok.Setter;

/**
 * @author happy
 * Api方法参数信息
 */
@Setter
@Getter
public class ApiMethodParamInfo extends FieldInfo {
    private boolean pathVariable = false;
    private boolean formParam = false;

    public ApiMethodParamInfo(String name, TypeInfo typeInfo) {
        super(name, typeInfo);
    }
}
