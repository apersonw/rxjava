package top.rxjava.third.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * 预授权码返回
 */
@Getter
@Setter
public class WxCpTpPreauthCode extends WxCpBaseResp {

    @SerializedName("pre_auth_code")
    String preAuthCode;

    @SerializedName("expires_in")
    Long expiresIn;

    public static WxCpTpPreauthCode fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpTpPreauthCode.class);
    }
}
