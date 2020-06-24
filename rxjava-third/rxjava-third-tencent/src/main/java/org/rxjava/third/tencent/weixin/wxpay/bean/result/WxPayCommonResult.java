package org.rxjava.third.tencent.weixin.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.w3c.dom.Document;

/**
 * 微信支付结果仅包含有return 和result等相关信息的的属性类
 */
@XStreamAlias("xml")
public class WxPayCommonResult extends BaseWxPayResult {
    @Override
    protected void loadXML(Document d) {
    }
}
