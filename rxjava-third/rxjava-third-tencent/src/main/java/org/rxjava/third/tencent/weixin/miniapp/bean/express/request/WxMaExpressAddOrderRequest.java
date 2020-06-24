package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.miniapp.bean.express.WxMaExpressDelivery;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 生成运单请求对象
 *
 * @author <a href="https://github.com/mr-xiaoyu">xiaoyu</a>
 * @since 2019-11-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressAddOrderRequest implements Serializable {

    private static final long serialVersionUID = -7538739003766268386L;


    /**
     * 订单来源
     * <p>
     * 是否必填： 是
     * 描述： 0为小程序订单，2为App或H5订单，填2则不发送物流服务通知
     */
    @SerializedName("add_source")
    private Integer addSource;

    /**
     * App或H5的appid
     * <p>
     * 是否必填： 否
     * 描述： add_source=2时必填，需和开通了物流助手的小程序绑定同一open帐号
     */
    @SerializedName("wx_appid")
    private String wxAppid;

    /**
     * 订单ID
     * <p>
     * 是否必填： 是
     * 描述： 须保证全局唯一，不超过512字节
     */
    @SerializedName("order_id")
    private String orderId;

    /**
     * 用户openid
     * <p>
     * 是否必填： 否
     * 描述： 当add_source=2时无需填写（不发送物流服务通知）
     */
    @SerializedName("openid")
    private String openid;

    /**
     * 快递公司ID
     * <p>
     * 是否必填： 是
     * 描述： 可通过getAllDelivery查询
     */
    @SerializedName("delivery_id")
    private String deliveryId;

    /**
     * 快递客户编码或者现付编码
     * <p>
     * 是否必填： 是
     */
    @SerializedName("biz_id")
    private String bizId;

    /**
     * 快递备注信息
     * <p>
     * 是否必填： 否
     * 描述： 比如"易碎物品"，不超过1024字节
     */
    @SerializedName("custom_remark")
    private String customRemark;

    /**
     * 订单标签id
     * <p>
     * 是否必填： 否
     * 描述： 用于平台型小程序区分平台上的入驻方，tagid须与入驻方账号一一对应，非平台型小程序无需填写该字段
     */
    @SerializedName("tagid")
    private Integer tagid;

    /**
     * 预期的上门揽件时间
     * <p>
     * 是否必填： 否
     * 描述： 顺丰必须传,0表示已事先约定取件时间；否则请传预期揽件时间戳，需大于当前时间，收件员会在预期时间附近上门。例如expect_time为“1557989929”，表示希望收件员将在2019年05月16日14:58:49-15:58:49内上门取货。说明：若选择 了预期揽件时间，请不要自己打单，由上门揽件的时候打印。
     */
    @SerializedName("expect_time")
    private Long expectTime;

    /**
     * 发件人信息
     * <p>
     * 是否必填： 是
     */
    private WxMaExpressOrderPerson sender;

    /**
     * 收件人信息
     * <p>
     * 是否必填： 是
     */
    private WxMaExpressOrderPerson receiver;

    /**
     * 包裹信息
     * <p>
     * 是否必填： 是
     * 描述： 将传递给快递公司
     */
    private WxMaExpressOrderCargo cargo;

    /**
     * 商品信息
     * <p>
     * 是否必填： 否
     * 描述： 会展示到物流服务通知和电子面单中
     */
    private WxMaExpressOrderShop shop;

    /**
     * 保价信息
     * <p>
     * 是否必填： 是
     */
    private WxMaExpressOrderInsured insured;

    /**
     * 服务类型
     * <p>
     * 是否必填： 是
     */
    private WxMaExpressDelivery.ServiceType service;


    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
