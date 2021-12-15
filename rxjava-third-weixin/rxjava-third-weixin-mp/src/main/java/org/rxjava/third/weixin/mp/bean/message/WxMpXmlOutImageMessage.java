package top.rxjava.third.weixin.mp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.common.util.xml.XStreamMediaIdConverter;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutImageMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = -2684778597067990723L;

    @XStreamAlias("Image")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    private String mediaId;

    public WxMpXmlOutImageMessage() {
        this.msgType = WxConsts.XmlMsgType.IMAGE;
    }

}
