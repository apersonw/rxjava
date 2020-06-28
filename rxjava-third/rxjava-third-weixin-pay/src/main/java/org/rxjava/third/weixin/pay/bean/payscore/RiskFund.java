package org.rxjava.third.weixin.pay.bean.payscore;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单风险金信息.
 */
@Data
@NoArgsConstructor
public class RiskFund implements Serializable {
    private static final long serialVersionUID = -3583406084396059152L;
    /**
     * name :  ESTIMATE_ORDER_COST
     * amount : 10000
     * description : 就餐的预估费用
     */
    @SerializedName("name")
    private String name;
    @SerializedName("amount")
    private int amount;
    @SerializedName("description")
    private String description;
}
