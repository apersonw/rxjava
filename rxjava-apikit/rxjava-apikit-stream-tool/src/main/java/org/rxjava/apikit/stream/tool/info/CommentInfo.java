package org.rxjava.apikit.stream.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 注释信息
 */
@Getter
@Setter
public class CommentInfo {
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
     * key: 方法名
     * value: 注释信息
     */
    private Map<String, CommentInfo> commentInfoMap;
}