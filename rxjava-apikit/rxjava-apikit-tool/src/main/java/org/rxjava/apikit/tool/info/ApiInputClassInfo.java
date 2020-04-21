package org.rxjava.apikit.tool.info;


import lombok.Getter;
import lombok.Setter;

/**
 * @author happy
 * Api方法输入类
 */
@Setter
@Getter
public class ApiInputClassInfo extends FieldInfo {
    private boolean pathVariable = false;
    private boolean formParam = false;
    private boolean requestParam = false;

    public ApiInputClassInfo(String name, ClassTypeInfo typeInfo) {
        super(name, typeInfo);
    }
}
