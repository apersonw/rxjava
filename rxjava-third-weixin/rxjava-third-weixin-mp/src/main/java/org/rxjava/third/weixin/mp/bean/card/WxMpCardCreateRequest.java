package top.rxjava.third.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 *
 */
@Data
public final class WxMpCardCreateRequest implements Serializable {
    private static final long serialVersionUID = 5951280855309617585L;

    @SerializedName("card")
    private AbstractCardCreateRequest cardCreateRequest;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static WxMpCardCreateRequest fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpCardCreateRequest.class);
    }
}
