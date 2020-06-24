package org.rxjava.third.tencent.weixin.mp.builder.kefu;

import org.rxjava.third.tencent.weixin.common.api.WxConsts;
import org.rxjava.third.tencent.weixin.mp.bean.kefu.WxMpKefuMessage;

/**
 * 图文消息builder
 * <p>
 * 用法:
 * WxMpKefuMessage m = WxMpKefuMessage.NEWS().mediaId("xxxxx").toUser(...).build();
 *
 * @author Binary Wang
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
