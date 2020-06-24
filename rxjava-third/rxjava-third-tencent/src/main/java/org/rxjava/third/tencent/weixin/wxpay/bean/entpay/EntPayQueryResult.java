package org.rxjava.third.tencent.weixin.wxpay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 企业付款查询返回结果.
 * Created by Binary Wang on 2016/10/19.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class EntPayQueryResult extends BaseWxPayResult {
    private static final long serialVersionUID = 3948485732447456947L;

    /**
     * 商户订单号.
     */
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    /**
     * 付款单号.
     */
    @XStreamAlias("detail_id")
    private String detailId;

    /**
     * 转账状态.
     */
    @XStreamAlias("status")
    private String status;

    /**
     * 失败原因.
     */
    @XStreamAlias("reason")
    private String reason;

    /**
     * 收款用户openid.
     */
    @XStreamAlias("openid")
    private String openid;

    /**
     * 收款用户姓名.
     */
    @XStreamAlias("transfer_name")
    private String transferName;

    /**
     * 付款金额.
     */
    @XStreamAlias("payment_amount")
    private Integer paymentAmount;

    /**
     * 发起转账的时间.
     */
    @XStreamAlias("transfer_time")
    private String transferTime;

    /**
     * 企业付款成功时间.
     */
    @XStreamAlias("payment_time")
    private String paymentTime;

    /**
     * 付款描述.
     */
    @XStreamAlias("desc")
    private String desc;

    @Override
    protected void loadXML(Document d) {
        partnerTradeNo = readXMLString(d, "partner_trade_no");
        detailId = readXMLString(d, "detail_id");
        status = readXMLString(d, "status");
        reason = readXMLString(d, "reason");
        openid = readXMLString(d, "openid");
        transferName = readXMLString(d, "transfer_name");
        paymentAmount = readXMLInteger(d, "payment_amount");
        transferTime = readXMLString(d, "transfer_time");
        paymentTime = readXMLString(d, "payment_time");
        desc = readXMLString(d, "desc");
    }
}
