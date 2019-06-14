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

    private JavadocInfo javadocInfo;

    public PropertyInfo(String name, TypeInfo typeInfo) {
        super(name, typeInfo);
    }
}
