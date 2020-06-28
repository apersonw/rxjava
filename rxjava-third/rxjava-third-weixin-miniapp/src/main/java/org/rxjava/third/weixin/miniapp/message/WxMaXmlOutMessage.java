package org.rxjava.third.weixin.miniapp.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.rxjava.third.weixin.common.util.xml.XStreamCDataConverter;
import org.rxjava.third.weixin.miniapp.config.WxMaConfig;
import org.rxjava.third.weixin.miniapp.util.crypt.WxMaCryptUtils;
import org.rxjava.third.weixin.miniapp.util.xml.XStreamTransformer;

import java.io.Serializable;

/**
 * 微信小程序输出给微信服务器的消息.
 */
@Data
@XStreamAlias("xml")
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxMaXmlOutMessage implements Serializable {
    private static final long serialVersionUID = 4241135225946919153L;

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String fromUserName;

    @XStreamAlias("CreateTime")
    protected Long createTime;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    protected String msgType;

    @SuppressWarnings("unchecked")
    public String toXml() {
        return XStreamTransformer.toXml((Class<WxMaXmlOutMessage>) this.getClass(), this);
    }

    /**
     * 转换成加密的xml格式.
     */
    public String toEncryptedXml(WxMaConfig config) {
        String plainXml = toXml();
        WxMaCryptUtils pc = new WxMaCryptUtils(config);
        return pc.encrypt(plainXml);
    }
}
