package top.rxjava.third.weixin.miniapp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * lineColor 包装类
 * 用于描述二维码（小程序码）颜色（RGB参数值），
 * 详情请查看文档 https://mp.weixin.qq.com/debug/wxadoc/dev/api/qrcode.html
 */
@Data
@AllArgsConstructor
public class WxMaCodeLineColor {
    private String r = "0", g = "0", b = "0";
}
