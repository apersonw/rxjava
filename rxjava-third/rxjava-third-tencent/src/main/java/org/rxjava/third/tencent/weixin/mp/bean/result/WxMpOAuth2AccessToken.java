package org.rxjava.third.tencent.weixin.mp.bean.result;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
 */
@Data
public class WxMpOAuth2AccessToken implements Serializable {
    private static final long serialVersionUID = -1345910558078620805L;

    private String accessToken;

    private int expiresIn = -1;

    private String refreshToken;

    private String openId;

    private String scope;

    /**
     * https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=11513156443eZYea&version=&lang=zh_CN.
     * 本接口在scope参数为snsapi_base时不再提供unionID字段。
     */
    private String unionId;

    public static WxMpOAuth2AccessToken fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpOAuth2AccessToken.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
