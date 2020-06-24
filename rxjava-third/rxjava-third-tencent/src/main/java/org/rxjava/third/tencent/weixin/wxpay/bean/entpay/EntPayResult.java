package org.rxjava.third.tencent.weixin.wxpay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 企业付款返回结果
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class EntPayResult extends BaseWxPayResult {
    private static final long serialVersionUID = 8523569987269603097L;

    /**
     * 商户号.
     */
    @XStreamAlias("mchid")
    private String mchId;

    /**
     * 商户appid.
     */
    @XStreamAlias("mch_appid")
    private String mchAppid;

    /**
     * 设备号.
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    //############以下字段在return_code 和result_code都为SUCCESS的时候有返回##############
    /**
     * 商户订单号.
     */
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    /**
     * 微信订单号.
     */
    @XStreamAlias("payment_no")
    private String paymentNo;

    /**
     * 微信支付成功时间.
     */
    @XStreamAlias("payment_time")
    private String paymentTime;

    @Override
    protected void loadXML(Document d) {
        mchId = readXMLString(d, "mchid");
        mchAppid = readXMLString(d, "mch_appid");
        deviceInfo = readXMLString(d, "device_info");
        partnerTradeNo = readXMLString(d, "partner_trade_no");
        paymentNo = readXMLString(d, "payment_no");
        paymentTime = readXMLString(d, "payment_time");
    }
}
