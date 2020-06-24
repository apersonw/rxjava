package org.rxjava.third.tencent.weixin.wxpay.bean.coupon;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 发送代金券响应结果类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayCouponSendResult extends BaseWxPayResult {
    /**
     * 字段名：设备号
     * 变量名：device_info
     * 是否必填：否
     * 示例值：123456sb
     * 类型：String(32)
     * 描述：微信支付分配的终端设备号，
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 字段名：代金券批次id
     * 变量名：coupon_stock_id
     * 是否必填：是
     * 示例值：1757
     * 类型：String
     * 描述：用户在商户appid下的唯一标识
     */
    @XStreamAlias("coupon_stock_id")
    private String couponStockId;

    /**
     * 字段名：返回记录数
     * 变量名：resp_count
     * 是否必填：是
     * 示例值：1
     * 类型：Int
     * 描述：返回记录数
     */
    @XStreamAlias("resp_count")
    private Integer respCount;

    /**
     * 字段名：成功记录数
     * 变量名：success_count
     * 是否必填：是
     * 示例值：1或者0
     * 类型：Int
     * 描述：成功记录数
     */
    @XStreamAlias("success_count")
    private Integer successCount;

    /**
     * 字段名：失败记录数
     * 变量名：failed_count
     * 是否必填：是
     * 示例值：1或者0
     * 类型：Int
     * 描述：失败记录数
     */
    @XStreamAlias("failed_count")
    private Integer failedCount;

    /**
     * 字段名：用户标识
     * 变量名：openid
     * 是否必填：是
     * 示例值：onqOjjrXT-776SpHnfexGm1_P7iE
     * 类型：String
     * 描述：用户在商户appid下的唯一标识
     */
    @XStreamAlias("openid")
    private String openid;

    /**
     * 字段名：返回码
     * 变量名：ret_code
     * 是否必填：是
     * 示例值：SUCCESS或者FAILED
     * 类型：String
     * 描述：返回码，SUCCESS/FAILED
     */
    @XStreamAlias("ret_code")
    private String retCode;

    /**
     * 字段名：代金券id
     * 变量名：coupon_id
     * 是否必填：是
     * 示例值：1870
     * 类型：String
     * 描述：对一个用户成功发放代金券则返回代金券id，即ret_code为SUCCESS的时候；如果ret_code为FAILED则填写空串""
     */
    @XStreamAlias("coupon_id")
    private String couponId;

    /**
     * 字段名：返回信息
     * 变量名：ret_msg
     * 是否必填：是
     * 示例值：失败描述信息，例如：“用户已达领用上限”
     * 类型：String
     * 描述：返回信息，当返回码是FAILED的时候填写，否则填空串“”
     */
    @XStreamAlias("ret_msg")
    private String retMsg;

    @Override
    protected void loadXML(Document d) {
        deviceInfo = readXMLString(d, "device_info");
        couponStockId = readXMLString(d, "coupon_stock_id");
        respCount = readXMLInteger(d, "resp_count");
        successCount = readXMLInteger(d, "success_count");
        failedCount = readXMLInteger(d, "failed_count");
        openid = readXMLString(d, "openid");
        retCode = readXMLString(d, "ret_code");
        couponId = readXMLString(d, "coupon_id");
        retMsg = readXMLString(d, "ret_msg");
    }
}
