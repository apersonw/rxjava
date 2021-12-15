package top.rxjava.third.weixin.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.common.util.xml.XStreamCDataConverter;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpXmlOutTextMessage extends WxCpXmlOutMessage {
    private static final long serialVersionUID = 2569239617185930232L;

    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String content;

    public WxCpXmlOutTextMessage() {
        this.msgType = WxConsts.XmlMsgType.TEXT;
    }

}
