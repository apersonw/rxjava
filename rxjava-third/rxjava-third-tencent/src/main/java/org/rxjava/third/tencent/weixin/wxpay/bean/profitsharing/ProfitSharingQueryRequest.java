package org.rxjava.third.tencent.weixin.wxpay.bean.profitsharing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import lombok.experimental.Accessors;
import org.rxjava.third.tencent.weixin.common.annotation.Required;
import org.rxjava.third.tencent.weixin.wxpay.bean.request.BaseWxPayRequest;
import org.rxjava.third.tencent.weixin.wxpay.constant.WxPayConstants;
import org.rxjava.third.tencent.weixin.wxpay.exception.WxPayException;

import java.util.Map;

/**
 * @author Wang GuangXin 2019/10/22 15:44
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class ProfitSharingQueryRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 3566332883053157102L;
    /**
     * 字段名：微信支付订单号.
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
     * 描述：查询分账结果，输入申请分账时的商户分账单号； 查询分账完结的执行结果，输入发起分账完结时的商户分账单号
     */
    @XStreamAlias("out_order_no")
    @Required
    private String outOrderNo;

    @Override
    protected void checkConstraints() throws WxPayException {
        this.setSignType(WxPayConstants.SignType.HMAC_SHA256);
    }

    @Override
    public boolean ignoreAppid() {
        return true;
    }

    @Override
    protected boolean ignoreSubAppId() {
        return true;
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("transaction_id", transactionId);
        map.put("out_order_no", outOrderNo);
    }
}
