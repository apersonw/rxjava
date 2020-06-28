package org.rxjava.third.weixin.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * 支付请求默认对象类
 */
@XStreamAlias("xml")
public class WxPayDefaultRequest extends BaseWxPayRequest {
    @Override
    protected void checkConstraints() {
        //do nothing
    }

    @Override
    protected boolean ignoreAppid() {
        return true;
    }

    @Override
    protected void storeMap(Map<String, String> map) {
    }
}
