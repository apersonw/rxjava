package top.rxjava.third.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

/**
 */
@Getter
@Setter
public class WxCpBaseResp {
    @SerializedName("errcode")
    protected Long errcode;

    @SerializedName("errmsg")
    protected String errmsg;

    public boolean success() {
        return getErrcode() == 0;
    }

    public static WxCpBaseResp fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpBaseResp.class);
    }
}
