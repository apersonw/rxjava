package top.rxjava.third.weixin.pay.bean.profitsharing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.rxjava.third.weixin.pay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
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
