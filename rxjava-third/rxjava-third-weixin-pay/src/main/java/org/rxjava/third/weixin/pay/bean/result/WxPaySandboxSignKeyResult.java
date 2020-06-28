package org.rxjava.third.weixin.pay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

/**
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPaySandboxSignKeyResult extends BaseWxPayResult {

    /**
     * 沙箱密钥
     * sandbox_signkey
     * 否
     * 013467007045764
     * String(32)
     * 返回的沙箱密钥
     */
    @XStreamAlias("sandbox_signkey")
    private String sandboxSignKey;

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        sandboxSignKey = readXMLString(d, "sandbox_signkey");
    }

}
