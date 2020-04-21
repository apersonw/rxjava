package org.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy
 * 字段信息
 */
@Setter
@Getter
public class FieldInfo {
    /**
     * 类型信息
     */
    private ClassTypeInfo classTypeInfo;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段注解
     */
    private List<AnnotationInfo> annotations = new ArrayList<>();

    public FieldInfo(String fieldName, ClassTypeInfo classTypeInfo) {
        this.fieldName = fieldName;
        this.classTypeInfo = classTypeInfo;
    }
}
