package org.rxjava.third.tencent.weixin.wxpay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * 支付请求默认对象类
 * Created by BinaryWang on 2017/6/18.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
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
