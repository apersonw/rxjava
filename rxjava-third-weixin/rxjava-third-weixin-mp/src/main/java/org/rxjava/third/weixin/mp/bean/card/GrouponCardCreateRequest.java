package top.rxjava.third.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GrouponCardCreateRequest extends AbstractCardCreateRequest implements Serializable {
    private static final long serialVersionUID = 7551441058859934512L;

    @SerializedName("card_type")
    private String cardType = "GROUPON";

    private GrouponCard groupon;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
