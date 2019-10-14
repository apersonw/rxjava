package org.rxjava.service.example.form;

import lombok.Data;

@Data
public class QrcodeCreateForm {
    /**
     * 图片类型
     */
    private String type;
    /**
     * 图片宽
     */
    private int width;
    /**
     * 图片高
     */
    private int height;
}
