package top.rxjava.third.weixin.cp.bean.messagebuilder;

import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.cp.bean.WxCpMessage;

/**
 * 语音消息builder
 * <p>
 * 用法: WxCustomMessage m = WxCustomMessage.VOICE().mediaId(...).toUser(...).build();
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder> {
    private String mediaId;

    public VoiceBuilder() {
        this.msgType = WxConsts.KefuMsgType.VOICE;
    }

    public VoiceBuilder mediaId(String media_id) {
        this.mediaId = media_id;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
