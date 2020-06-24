package org.rxjava.third.tencent.weixin.wxpay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 企业付款到银行卡的响应结果.
 * Created by Binary Wang on 2017/12/21.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class EntPayBankResult extends BaseWxPayResult {
    private static final long serialVersionUID = 3449707749935227689L;

    /**
     * 代付金额.
     */
    @XStreamAlias("amount")
    private Integer amount;

    /**
     * 商户企业付款单号.
     */
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    //############以下字段在return_code 和result_code都为SUCCESS的时候有返回##############
    /**
     * 微信企业付款单号.
     * 代付成功后，返回的内部业务单号
     */
    @XStreamAlias("payment_no")
    private String paymentNo;

    /**
     * 手续费金额.
     * RMB：分
     */
    @XStreamAlias("cmms_amt")
    private Integer cmmsAmount;

    @Override
    protected void loadXML(Document d) {
        amount = readXMLInteger(d, "amount");
        partnerTradeNo = readXMLString(d, "partner_trade_no");
        paymentNo = readXMLString(d, "payment_no");
        cmmsAmount = readXMLInteger(d, "cmms_amt");
    }
}
