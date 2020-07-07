package org.rxjava.third.weixin.pay.bean.coupon;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.rxjava.third.weixin.common.annotation.Required;
import org.rxjava.third.weixin.pay.bean.request.BaseWxPayRequest;

import java.util.Map;

/**
 * 查询代金券信息请求对象类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayCouponInfoQueryRequest extends BaseWxPayRequest {
    /**
     * 字段名：代金券id
     * 变量名：coupon_id
     * 是否必填：是
     * 示例值：1757
     * 类型：String
     * 说明：代金券id
     */
    @Required
    @XStreamAlias("coupon_id")
    private String couponId;

    /**
     * 字段名：代金券批次号
     * 变量名：stock_id
     * 是否必填：是
     * 示例值：58818
     * 类型：String
     * 说明：代金劵对应的批次号
     */
    @Required
    @XStreamAlias("stock_id")
    private String stockId;

    /**
     * 字段名：用户openid
     * 变量名：openid
     * 是否必填：是
     * 示例值：onqOjjrXT-776SpHnfexGm1_P7iE
     * 类型：String
     * 说明：Openid信息，用户在appid下的openid。
     */
    @Required
    @XStreamAlias("openid")
    private String openid;

    /**
     * 字段名：操作员
     * 变量名：op_user_id
     * 是否必填：否
     * 示例值：10000098
     * 类型：String(32)
     * 说明：操作员帐号, 默认为商户号,可在商户平台配置操作员对应的api权限
     */
    @XStreamAlias("op_user_id")
    private String opUserId;

    /**
     * 字段名：设备号
     * 变量名：device_info
     * 是否必填：否
     * 示例值：
     * 类型：String(32)
     * 说明：微信支付分配的终端设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 字段名：协议版本
     * 变量名：version
     * 是否必填：否
     * 示例值：1.0
     * 类型：String(32)
     * 说明：默认1.0
     */
    @XStreamAlias("version")
    private String version;

    /**
     * 字段名：协议类型
     * 变量名：type
     * 是否必填：否
     * 示例值：XML
     * 类型：String(32)
     * 说明：XML【目前仅支持默认XML】
     */
    @XStreamAlias("type")
    private String type;


    @Override
    protected void checkConstraints() {
        //do nothing
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("coupon_id", couponId);
        map.put("stock_id", stockId);
        map.put("openid", openid);
        map.put("op_user_id", opUserId);
        map.put("device_info", deviceInfo);
        map.put("version", version);
        map.put("type", type);
    }

}
