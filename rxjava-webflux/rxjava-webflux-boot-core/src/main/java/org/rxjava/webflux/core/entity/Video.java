package org.rxjava.webflux.core.entity;

import lombok.Data;

/**
 * @author happy 2019-07-16 22:36
 * 视频
 */
@Data
public class Video {
    /**
     * 宽：单位像素
     */
    private int width;
    /**
     * 高：单位像素
     */
    private int height;
    /**
     * 存储Key
     */
    private String key;
}
