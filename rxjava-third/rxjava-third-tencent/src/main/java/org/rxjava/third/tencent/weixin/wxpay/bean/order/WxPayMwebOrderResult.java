package org.rxjava.third.tencent.weixin.wxpay.bean.order;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信H5支付统一下单后发起支付拼接所需参数实现类.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPayMwebOrderResult implements Serializable {
    private static final long serialVersionUID = 8866329695767762066L;

    @XStreamAlias("mwebUrl")
    private String mwebUrl;
}
