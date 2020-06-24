package org.rxjava.third.tencent.weixin.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

import java.io.Serializable;

/**
 * 获取微信刷脸调用凭证返回结果.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayFaceAuthInfoResult extends BaseWxPayResult implements Serializable {
    private static final long serialVersionUID = -65138145275211272L;

    /**
     * SDK调用凭证.
     */
    @XStreamAlias("authinfo")
    private String authinfo;

    /**
     * authinfo的有效时间, 单位秒.
     */
    @XStreamAlias("expires_in")
    private String expiresIn;

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        authinfo = readXMLString(d, "authinfo");
        expiresIn = readXMLString(d, "expires_in");
    }

}
