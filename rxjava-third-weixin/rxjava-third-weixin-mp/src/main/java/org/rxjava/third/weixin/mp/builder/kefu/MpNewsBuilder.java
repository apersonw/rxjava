package top.rxjava.third.weixin.mp.builder.kefu;

import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.mp.bean.kefu.WxMpKefuMessage;

/**
 * 图文消息builder
 * 用法:
 * WxMpKefuMessage m = WxMpKefuMessage.NEWS().mediaId("xxxxx").toUser(...).build();
 */
public final class MpNewsBuilder extends BaseBuilder<MpNewsBuilder> {
    private String mediaId;

    public MpNewsBuilder() {
        this.msgType = WxConsts.KefuMsgType.MPNEWS;
    }

    public MpNewsBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setMpNewsMediaId(this.mediaId);
        return m;
    }
}
