package org.rxjava.third.tencent.weixin.wxpay.bean.profitsharing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * @author Wang GuangXin 2019/10/22 14:54
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class ProfitSharingReceiverResult extends BaseWxPayResult {
    private static final long serialVersionUID = 876204163877798066L;
    /**
     * 分账接收方.
     */
    @XStreamAlias("receiver")
    private String receiver;

    @Override
    protected void loadXML(Document d) {
        receiver = readXMLString(d, "receiver");
    }
}
