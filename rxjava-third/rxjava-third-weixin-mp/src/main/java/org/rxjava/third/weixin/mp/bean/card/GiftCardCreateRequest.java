package org.rxjava.third.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GiftCardCreateRequest extends AbstractCardCreateRequest implements Serializable {
    private static final long serialVersionUID = 1283655452584811858L;

    @SerializedName("card_type")
    private String cardType = "GIFT";

    private GiftCard gift;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
