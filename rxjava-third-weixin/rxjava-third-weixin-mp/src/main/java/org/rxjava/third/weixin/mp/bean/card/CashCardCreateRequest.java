package org.rxjava.third.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CashCardCreateRequest extends AbstractCardCreateRequest implements Serializable {
    private static final long serialVersionUID = 8251635683908302125L;

    @SerializedName("card_type")
    private String cardType = "CASH";

    private CashCard cash;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
