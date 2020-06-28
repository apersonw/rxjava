package org.rxjava.third.weixin.pay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

/**
 * 关闭订单结果对象类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayOrderCloseResult extends BaseWxPayResult {

    /**
     * 业务结果描述
     */
    @XStreamAlias("result_msg")
    private String resultMsg;

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        resultMsg = readXMLString(d, "result_msg");
    }

}
