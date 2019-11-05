package org.rxjava.apikit.stream.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 方法注释信息
 */
@Getter
@Setter
public class MethodCommentInfo {
    /**
     * 注释
     */
    private String comment;
    /**
     * 描述
     */
    private String desc;
    /**
     * 字段map信息
     */
    private Map<String, FieldCommentInfo> fieldCommentInfoMap;
}
