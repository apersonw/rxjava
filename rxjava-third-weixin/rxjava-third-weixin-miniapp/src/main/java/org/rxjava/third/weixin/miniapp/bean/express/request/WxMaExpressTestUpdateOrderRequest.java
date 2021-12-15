package top.rxjava.third.weixin.miniapp.bean.express.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 模拟快递公司更新订单状态请求对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressTestUpdateOrderRequest implements Serializable {
    private static final long serialVersionUID = -3701602332580704140L;

    /**
     * 商户id
     * <p>
     * 是否必填： 是
     * 描述： 需填test_biz_id,默认值已设置
     */
    @SerializedName("biz_id")
    @Builder.Default
    private final String bizId = "test_biz_id";

    /**
     * 快递公司id
     * <p>
     * 是否必填： 是
     * 描述： 需填TEST,默认值已设置
     */
    @SerializedName("delivery_id")
    @Builder.Default
    private final String deliveryId = "TEST";

    /**
     * 订单号
     * <p>
     * 是否必填： 是
     */
    @SerializedName("order_id")
    private String orderId;

    /**
     * 运单号
     * <p>
     * 是否必填： 是
     */
    @SerializedName("waybill_id")
    private String waybillId;

    /**
     * 轨迹变化 Unix 时间戳
     * <p>
     * 是否必填： 是
     */
    @SerializedName("action_time")
    private Long actionTime;

    /**
     * 轨迹变化类型
     * <p>
     * 是否必填： 是
     */
    @SerializedName("action_type")
    private Integer actionType;

    /**
     * 轨迹变化具体信息说明,使用UTF-8编码
     * <p>
     * 是否必填： 是
     */
    @SerializedName("action_msg")
    private String actionMsg;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }

}
