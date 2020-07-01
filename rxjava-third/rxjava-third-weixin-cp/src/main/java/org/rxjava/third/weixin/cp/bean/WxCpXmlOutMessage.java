package org.rxjava.third.weixin.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import org.rxjava.third.weixin.common.util.xml.XStreamCDataConverter;
import org.rxjava.third.weixin.cp.bean.outxmlbuilder.*;
import org.rxjava.third.weixin.cp.config.WxCpConfigStorage;
import org.rxjava.third.weixin.cp.util.crypto.WxCpCryptUtil;
import org.rxjava.third.weixin.cp.util.xml.XStreamTransformer;

import java.io.Serializable;

/**
 * 被动回复消息.
 * https://work.weixin.qq.com/api/doc#12975
 */
@XStreamAlias("xml")
@Data
public abstract class WxCpXmlOutMessage implements Serializable {
    private static final long serialVersionUID = 1418629839964153110L;

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

    /**
     * 获得文本消息builder.
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder.
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder.
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder.
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得图文消息builder.
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    protected String toXml() {
        return XStreamTransformer.toXml((Class) this.getClass(), this);
    }

    /**
     * 转换成加密的xml格式.
     */
    public String toEncryptedXml(WxCpConfigStorage wxCpConfigStorage) {
        String plainXml = toXml();
        WxCpCryptUtil pc = new WxCpCryptUtil(wxCpConfigStorage);
        return pc.encrypt(plainXml);
    }
}
