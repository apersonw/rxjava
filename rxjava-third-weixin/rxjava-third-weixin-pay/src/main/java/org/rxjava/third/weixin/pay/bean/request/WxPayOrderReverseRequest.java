package top.rxjava.third.weixin.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import top.rxjava.third.weixin.pay.exception.WxPayException;

import java.util.Map;

/**
 * 撤销订单请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayOrderReverseRequest extends BaseWxPayRequest {

    /**
     * 微信订单号
     * transaction_id
     * String(28)
     * 1217752501201400000000000000
     * 微信的订单号，优先使用
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 商户订单号
     * out_trade_no
     * String(32)
     * 1217752501201400000000000000
     * 商户系统内部的订单号
     * transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @Override
    protected void checkConstraints() throws WxPayException {
        if (StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)) {
            throw new WxPayException("transaction_id 和 out_trade_no不能同时为空！");
        }
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("transaction_id", transactionId);
        map.put("out_trade_no", outTradeNo);
    }

}
