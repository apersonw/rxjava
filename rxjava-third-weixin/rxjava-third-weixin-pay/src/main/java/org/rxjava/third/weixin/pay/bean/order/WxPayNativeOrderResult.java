package top.rxjava.third.weixin.pay.bean.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信扫码支付统一下单后发起支付拼接所需参数实现类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPayNativeOrderResult implements Serializable {
    private static final long serialVersionUID = 887792717425241444L;

    private String codeUrl;
}
