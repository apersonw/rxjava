package org.rxjava.third.tencent.weixin.wxpay.bean.profitsharing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.rxjava.third.tencent.weixin.common.annotation.Required;
import org.rxjava.third.tencent.weixin.wxpay.bean.request.BaseWxPayRequest;
import org.rxjava.third.tencent.weixin.wxpay.constant.WxPayConstants;

import java.util.Map;

/**
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class ProfitSharingFinishRequest extends BaseWxPayRequest {

    private static final long serialVersionUID = -4265779954583596627L;

    /**
     * 字段名：微信订单号.
     * 变量名：transaction_id
     * 是否必填：是
     * String(32)
     * 示例值：4208450740201411110007820472
     * 描述：微信支付订单号
     */
    @XStreamAlias("transaction_id")
    @Required
    private String transactionId;

    /**
     * 字段名：商户分账单号.
     * 变量名：out_order_no
     * 是否必填：是
     * String(64)
     * 示例值：P20150806125346
     * 描述：商户系统内部的分账单号，在商户系统内部唯一（单次分账、多次分账、完结分账应使用不同的商户分账单号），同一分账单号多次请求等同一次。只能是数字、大小写字母_-|*@
     */
    @XStreamAlias("out_order_no")
    @Required
    private String outOrderNo;

    /**
     * 字段名：分账完结描述.
     * 变量名：out_order_no
     * 是否必填：是
     * String(80)
     * 示例值：分账已完成
     * 描述：分账完结的原因描述
     */
    @XStreamAlias("description")
    @Required
    private String description;

    @Override
    protected void checkConstraints() {
        this.setSignType(WxPayConstants.SignType.HMAC_SHA256);
    }

    @Override
    protected boolean ignoreSubAppId() {
        return true;
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("transaction_id", transactionId);
        map.put("out_order_no", outOrderNo);
        map.put("description", description);
    }
}
