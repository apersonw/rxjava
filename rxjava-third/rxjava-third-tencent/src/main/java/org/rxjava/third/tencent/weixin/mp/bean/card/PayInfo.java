package org.rxjava.third.tencent.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 支付功能.
 *
 * @author yuanqixun
 * date:2018-08-25 00:33
 */
@Data
public class PayInfo implements Serializable {

    /**
     * 刷卡功能
     */
    @SerializedName("swipe_card")
    private SwipeCard swipeCard;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
