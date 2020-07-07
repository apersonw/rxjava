package org.rxjava.third.weixin.pay.bean.profitsharing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.weixin.pay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class ProfitSharingResult extends BaseWxPayResult {
    /**
     * 微信订单号.
     */
    @XStreamAlias("transaction_id")
    private String transactionId;
    /**
     * 商户分账单号.
     */
    @XStreamAlias("out_order_no")
    private String outOrderNo;
    /**
     * 微信分账单号.
     */
    @XStreamAlias("order_id")
    private String orderId;

    @Override
    protected void loadXML(Document d) {
        transactionId = readXMLString(d, "transaction_id");
        outOrderNo = readXMLString(d, "out_order_no");
        orderId = readXMLString(d, "order_id");
    }
}
