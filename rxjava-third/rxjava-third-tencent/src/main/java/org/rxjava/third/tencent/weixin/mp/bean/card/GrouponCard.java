package org.rxjava.third.tencent.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * .
 *
 * @author leeis
 * @Date 2018/12/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class GrouponCard extends Card implements Serializable {

    private static final long serialVersionUID = 3221312561666697005L;

    /**
     * 团购券专用，团购详情
     */
    @SerializedName("deal_detail")
    private String dealDetail;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static GrouponCard fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, GrouponCard.class);
    }
}
