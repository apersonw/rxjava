package org.rxjava.apikit.stream.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 类注释信息
 */
@Getter
@Setter
public class ClassCommentInfo {
    /**
     * 注释
     */
    private String comment;
    /**
     * 描述
     */
    private String desc;
    /**
     * 方法注释Map
     */
    private Map<String, MethodCommentInfo> methodCommentMap;
}
