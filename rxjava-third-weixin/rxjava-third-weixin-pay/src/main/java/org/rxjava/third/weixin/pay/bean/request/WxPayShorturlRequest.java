package top.rxjava.third.weixin.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

import java.util.Map;

/**
 * 转换短链接请求对象类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayShorturlRequest extends BaseWxPayRequest {
    /**
     * URL链接
     * long_url
     * 是
     * String(512)
     * weixin：//wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXXX&time_stamp=XXXXXX&nonce_str=XXXXX
     * 需要转换的URL，签名用原串，传输需URLencode
     */
    @XStreamAlias("long_url")
    private String longUrl;

    @Override
    protected void checkConstraints() {
        //do nothing
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("long_url", longUrl);
    }
}
