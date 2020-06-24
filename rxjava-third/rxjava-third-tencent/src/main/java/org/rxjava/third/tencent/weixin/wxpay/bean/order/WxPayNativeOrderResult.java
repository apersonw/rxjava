package org.rxjava.third.tencent.weixin.wxpay.bean.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信扫码支付统一下单后发起支付拼接所需参数实现类
 * Created by Binary Wang on 2017-9-1.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPayNativeOrderResult implements Serializable {
    private static final long serialVersionUID = 887792717425241444L;

    private String codeUrl;
}
