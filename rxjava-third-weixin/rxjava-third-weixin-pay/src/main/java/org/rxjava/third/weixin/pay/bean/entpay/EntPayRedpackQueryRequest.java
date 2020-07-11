package org.rxjava.third.weixin.pay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.rxjava.third.weixin.pay.bean.request.BaseWxPayRequest;
import org.rxjava.third.weixin.pay.exception.WxPayException;

import java.util.Map;

/**
 * 红包发送记录查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class EntPayRedpackQueryRequest extends BaseWxPayRequest {


    /**
     * 商户订单号
     */
    @XStreamAlias("mch_billno")
    private String mchBillNo;


    @Override
    protected void checkConstraints() throws WxPayException {

    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("mch_billno", mchBillNo);
    }
}