package org.rxjava.third.tencent.weixin.wxpay.bean.notify;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 扫码支付通知回调类.
 * 具体定义，请查看文档：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxScanPayNotifyResult extends BaseWxPayResult {
    private static final long serialVersionUID = 3381324564266118986L;

    /**
     * 用户标识.
     */
    @XStreamAlias("openid")
    private String openid;

    /**
     * 是否关注公众账号.
     * 仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注
     */
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    /**
     * 商品ID.
     * 商户定义的商品id 或者订单号
     */
    @XStreamAlias("product_id")
    private String productId;

    @Override
    protected void loadXML(Document d) {
        openid = readXMLString(d, "openid");
        isSubscribe = readXMLString(d, "is_subscribe");
        productId = readXMLString(d, "product_id");
    }

}
