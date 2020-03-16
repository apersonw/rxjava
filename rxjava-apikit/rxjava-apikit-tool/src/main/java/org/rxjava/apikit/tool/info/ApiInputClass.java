package org.rxjava.apikit.tool.info;


import lombok.Getter;
import lombok.Setter;

/**
 * @author happy
 * Api方法输入类
 */
@Setter
@Getter
public class ApiInputClass extends FieldInfo {
    private boolean pathVariable = false;
    private boolean formParam = false;

    public ApiInputClass(String name, TypeInfo typeInfo) {
        super(name, typeInfo);
    }
}
