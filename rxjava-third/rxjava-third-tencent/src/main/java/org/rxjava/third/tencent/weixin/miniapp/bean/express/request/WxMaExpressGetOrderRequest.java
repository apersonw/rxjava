package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 获取运单请求对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressGetOrderRequest implements Serializable {
    private static final long serialVersionUID = 8239315305577639778L;

    /**
     * 订单ID
     * <p>
     * 是否必填： 是
     */
    @SerializedName("order_id")
    private String orderId;

    /**
     * 用户openid
     * <p>
     * 是否必填： 否
     * 描述： 当add_source=2时无需填写（不发送物流服务通知）
     */
    private String openid;

    /**
     * 快递公司ID
     * <p>
     * 是否必填： 是
     */
    @SerializedName("delivery_id")
    private String deliveryId;

    /**
     * 运单ID
     * <p>
     * 是否必填： 是
     */
    @SerializedName("waybill_id")
    private String waybillId;


    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
