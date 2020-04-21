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
    private boolean pathParam = false;
    private boolean validParam = false;
    private boolean requestParam = false;
    private boolean jsonParam = false;

    public ApiInputClassInfo(String name, ClassTypeInfo typeInfo) {
        super(name, typeInfo);
    }
}
