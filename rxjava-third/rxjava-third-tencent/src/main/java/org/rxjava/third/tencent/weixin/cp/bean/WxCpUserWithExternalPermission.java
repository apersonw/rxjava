package org.rxjava.third.tencent.weixin.cp.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

/**
 */
@Data
public class WxCpUserWithExternalPermission {
    @SerializedName("errcode")
    @Expose
    private Long errCode;
    @SerializedName("errmsg")
    @Expose
    private String errMsg;

    @SerializedName("follow_user")
    @Expose
    private List<String> followers = null;

    public static WxCpUserWithExternalPermission fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpUserWithExternalPermission.class);
    }
}
