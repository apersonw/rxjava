package org.rxjava.third.weixin.miniapp.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参考文档 https://developers.weixin.qq.com/miniprogram/dev/api-backend/templateMessage.send.html
 */
@Data
@NoArgsConstructor
public class WxMaTemplateData {
    private String name;
    private String value;
    private String color;

    public WxMaTemplateData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public WxMaTemplateData(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }


}
