package top.rxjava.third.weixin.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

import java.util.Map;

/**
 * 授权码查询openid接口请求对象类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayAuthcode2OpenidRequest extends BaseWxPayRequest {

    /**
     * 授权码
     * auth_code
     * 是
     * String(128)
     * 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     */
    @XStreamAlias("auth_code")
    private String authCode;

    @Override
    protected void checkConstraints() {
        // nothing to do
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("auth_code", authCode);
    }

}
