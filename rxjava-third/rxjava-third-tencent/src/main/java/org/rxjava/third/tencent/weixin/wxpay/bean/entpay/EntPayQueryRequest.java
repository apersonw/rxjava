package org.rxjava.third.tencent.weixin.wxpay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.rxjava.third.tencent.weixin.common.annotation.Required;
import org.rxjava.third.tencent.weixin.common.util.json.WxGsonBuilder;
import org.rxjava.third.tencent.weixin.wxpay.bean.request.BaseWxPayRequest;

import java.util.Map;

/**
 * 企业付款请求对象.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class EntPayQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 1972288742207813985L;

    /**
     * 字段名：商户订单号.
     * 变量名：partner_trade_no
     * 是否必填：是
     * 示例值：10000098201411111234567890
     * 类型：String
     * 描述商户订单号
     */
    @Required
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    @Override
    protected void checkConstraints() {
        //do nothing
    }

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"sign_type"};
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("partner_trade_no", partnerTradeNo);
    }
}
