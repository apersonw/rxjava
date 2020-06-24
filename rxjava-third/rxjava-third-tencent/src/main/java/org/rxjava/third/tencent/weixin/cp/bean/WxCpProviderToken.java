package org.rxjava.third.tencent.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * 服务商凭证.
 */
@Data
public class WxCpProviderToken {
    /**
     * 服务商的access_token，最长为512字节。
     */
    @SerializedName("provider_access_token")
    private String providerAccessToken;

    /**
     * provider_access_token有效期（秒）
     */
    @SerializedName("expires_in")
    private Integer expiresIn;

    public static WxCpProviderToken fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpProviderToken.class);
    }
}
