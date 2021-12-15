package top.rxjava.third.weixin.mp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.common.util.xml.XStreamCDataConverter;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpXmlOutTextMessage extends WxMpXmlOutMessage {
    private static final long serialVersionUID = -3972786455288763361L;

    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String content;

    public WxMpXmlOutTextMessage() {
        this.msgType = WxConsts.XmlMsgType.TEXT;
    }

}
