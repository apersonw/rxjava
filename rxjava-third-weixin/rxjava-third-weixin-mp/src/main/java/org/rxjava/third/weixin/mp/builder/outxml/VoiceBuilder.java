package top.rxjava.third.weixin.mp.builder.outxml;

import top.rxjava.third.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;

/**
 * 语音消息builder
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxMpXmlOutVoiceMessage> {

    private String mediaId;

    public VoiceBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxMpXmlOutVoiceMessage build() {
        WxMpXmlOutVoiceMessage m = new WxMpXmlOutVoiceMessage();
        setCommon(m);
        m.setMediaId(this.mediaId);
        return m;
    }

}
