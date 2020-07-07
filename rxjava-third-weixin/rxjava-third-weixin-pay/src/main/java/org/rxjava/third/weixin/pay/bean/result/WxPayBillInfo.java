package org.rxjava.third.weixin.pay.bean.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * 交易时间:2017-04-06 01:00:02 公众账号ID: 商户号: 子商户号:0 设备号:WEB 微信订单号: 商户订单号:2017040519091071873216 用户标识: 交易类型:NATIVE
 * 交易状态:REFUND 付款银行:CFT 货币种类:CNY 总金额:0.00 企业红包金额:0.00 微信退款单号: 商户退款单号:20170406010000933 退款金额:0.01 企业红包退款金额:0.00
 * 退款类型:ORIGINAL 退款状态:SUCCESS 商品名称: 商户数据包: 手续费:0.00000 费率 :0.60%
 */
@Data
@NoArgsConstructor
public class WxPayBillInfo implements Serializable {
    private static final long serialVersionUID = 2226245109137435453L;

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

    /**
     * 交易时间.
     */
    private String tradeTime;
    /**
     * 公众账号ID.
     */
    private String appId;
    /**
     * 商户号.
     */
    private String mchId;
    /**
     * 特约商户号.
     */
    private String subMchId;
    /**
     * 设备号.
     */
    private String deviceInfo;
    /**
     * 微信订单号.
     */
    private String transactionId;
    /**
     * 商户订单号.
     */
    private String outTradeNo;
    /**
     * 用户标识.
     */
    private String openId;
    /**
     * 交易类型.
     */
    private String tradeType;
    /**
     * 交易状态.
     */
    private String tradeState;
    /**
     * 付款银行.
     */
    private String bankType;
    /**
     * 货币种类.
     */
    private String feeType;
    /**
     * 应结订单金额.
     */
    private String totalFee;
    /**
     * 代金券金额.
     */
    private String couponFee;
    /**
     * 微信退款单号.
     */
    private String refundId;
    /**
     * 商户退款单号.
     */
    private String outRefundNo;
    /**
     * 退款金额.
     */
    private String settlementRefundFee;
    /**
     * 充值券退款金额.
     */
    private String couponRefundFee;
    /**
     * 退款类型.
     */
    private String refundChannel;
    /**
     * 退款状态.
     */
    private String refundState;
    /**
     * 商品名称.
     */
    private String body;
    /**
     * 商户数据包.
     */
    private String attach;
    /**
     * 手续费.
     */
    private String poundage;
    /**
     * 费率.
     */
    private String poundageRate;
    /**
     * 订单金额.
     */
    private String totalAmount;
    /**
     * 申请退款金额.
     */
    private String appliedRefundAmount;
    /**
     * 费率备注.
     */
    private String feeRemark;
    /**
     * 退款申请时间
     */
    private String refundTime;
    /**
     * 退款成功时间
     */
    private String refundSuccessTime;
}
