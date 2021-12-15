package top.rxjava.third.weixin.pay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

/**
 * 授权码查询openid接口请求结果类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayAuthcode2OpenidResult extends BaseWxPayResult {
    /**
     * 用户标识.
     * openid
     * 是
     * String(128)
     * 用户在商户appid下的唯一标识
     */
    @XStreamAlias("openid")
    private String openid;

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        openid = readXMLString(d, "openid");
    }

}
