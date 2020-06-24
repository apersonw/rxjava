package org.rxjava.third.tencent.weixin.wxpay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 企业付款获取RSA加密公钥接口返回结果类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XStreamAlias("xml")
public class GetPublicKeyResult extends BaseWxPayResult {
    /**
     * 商户号.
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 密钥
     */
    @XStreamAlias("pub_key")
    private String pubKey;

    @Override
    protected void loadXML(Document d) {
        mchId = readXMLString(d, "mch_id");
        pubKey = readXMLString(d, "pub_key");
    }
}
