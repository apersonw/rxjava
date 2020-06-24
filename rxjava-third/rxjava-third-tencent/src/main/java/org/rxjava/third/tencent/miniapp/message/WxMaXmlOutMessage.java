package org.rxjava.third.tencent.miniapp.message;

import org.rxjava.third.tencent.miniapp.config.WxMaConfig;
import org.rxjava.third.tencent.miniapp.util.crypt.WxMaCryptUtils;
import org.rxjava.third.tencent.miniapp.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.rxjava.third.tencent.common.util.xml.XStreamCDataConverter;

import java.io.Serializable;

/**
 * 微信小程序输出给微信服务器的消息.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * @date 2019-06-22
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
