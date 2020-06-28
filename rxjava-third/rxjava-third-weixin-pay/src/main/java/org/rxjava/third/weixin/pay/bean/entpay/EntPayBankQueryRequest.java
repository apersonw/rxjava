package org.rxjava.third.weixin.pay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 企业付款到银行卡的请求对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class EntPayBankQueryRequest extends EntPayQueryRequest {
    private static final long serialVersionUID = -479088843124447119L;

    @Override
    protected boolean ignoreAppid() {
        return true;
    }
}
