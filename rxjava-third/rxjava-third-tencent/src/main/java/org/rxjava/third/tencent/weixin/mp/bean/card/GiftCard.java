package org.rxjava.third.tencent.weixin.mp.bean.card;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 */
@Data
public final class GiftCard extends Card implements Serializable {

    private static final long serialVersionUID = -6168739707511792266L;

    /**
     * 兑换券专用，填写兑换内容的名称。
     */
    private String gift;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static GiftCard fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, GiftCard.class);
    }
}
