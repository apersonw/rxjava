package org.rxjava.apikit.tool.info;


import lombok.Getter;
import lombok.Setter;

/**
 * @author happy
 * 属性信息
 */
@Setter
@Getter
public class PropertyInfo extends FieldInfo {

    private JavaDocInfo javadocInfo;

    public PropertyInfo(String name, ClassTypeInfo classTypeInfo) {
        super(name, classTypeInfo);
    }
}
