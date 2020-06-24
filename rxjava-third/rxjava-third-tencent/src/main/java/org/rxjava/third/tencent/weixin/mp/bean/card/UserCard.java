package org.rxjava.third.tencent.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 用户已领卡圈对象
 */
@Data
public class UserCard implements java.io.Serializable {
    /**
     * 用户卡券code码
     */
    @SerializedName("code")
    private String code;

    /**
     * 卡券ID
     */
    @SerializedName("card_id")
    private String cardId;

    public static UserCard fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, UserCard.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
