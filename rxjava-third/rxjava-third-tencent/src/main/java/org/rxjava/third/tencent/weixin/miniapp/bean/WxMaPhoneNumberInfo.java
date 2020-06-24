package org.rxjava.third.tencent.weixin.miniapp.bean;

import lombok.Data;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 微信用户绑定的手机号相关信息
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public class WxMaPhoneNumberInfo implements Serializable {
    private static final long serialVersionUID = 6719822331555402137L;

    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Watermark watermark;

    public static WxMaPhoneNumberInfo fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaPhoneNumberInfo.class);
    }

    @Data
    public static class Watermark implements Serializable {
        private static final long serialVersionUID = 2375642809946928650L;

        private String timestamp;
        private String appid;
    }
}
