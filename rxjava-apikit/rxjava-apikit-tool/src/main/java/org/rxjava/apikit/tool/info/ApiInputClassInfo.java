package org.rxjava.apikit.tool.info;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author happy
 * Api方法输入类
 */
@Setter
@Getter
@ToString
public class ApiInputClassInfo extends FieldInfo {
    private boolean pathParam = false;
    private boolean validParam = false;
    private boolean requestParam = false;
    private boolean jsonParam = false;

    public ApiInputClassInfo(String name, ClassTypeInfo classTypeInfo) {
        super(name, classTypeInfo);
    }
}
