package org.rxjava.third.weixin.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.common.util.xml.XStreamMediaIdConverter;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpXmlOutImageMessage extends WxCpXmlOutMessage {
    private static final long serialVersionUID = -1099446240667237313L;

    @XStreamAlias("Image")
    @XStreamConverter(value = XStreamMediaIdConverter.class)
    private String mediaId;

    public WxCpXmlOutImageMessage() {
        this.msgType = WxConsts.XmlMsgType.IMAGE;
    }

}
