package org.rxjava.third.weixin.mp.bean.membercard;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.weixin.mp.bean.card.MemberCardCreateRequest;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

@Data
public final class WxMpMemberCardCreateMessage implements Serializable {

    @SerializedName("card")
    private MemberCardCreateRequest cardCreateRequest;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static WxMpMemberCardCreateMessage fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMemberCardCreateMessage.class);
    }
}
