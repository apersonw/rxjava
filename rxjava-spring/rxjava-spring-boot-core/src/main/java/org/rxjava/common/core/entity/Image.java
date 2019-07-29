package org.rxjava.common.core.entity;

import lombok.Data;

/**
 * @author happy 2019-07-16 22:36
 * 图片
 */
@Data
public class Image {
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
