package top.rxjava.third.weixin.pay.bean.payscore;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后付费商户优惠.
 */
@Data
@NoArgsConstructor
public class PostDiscount implements Serializable {
    private static final long serialVersionUID = 2764537888242763379L;
    /**
     * name : 满20减1元
     * description : 不与其他优惠叠加
     */
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("count")
    private int count;
    @SerializedName("amount")
    private int amount;
}
