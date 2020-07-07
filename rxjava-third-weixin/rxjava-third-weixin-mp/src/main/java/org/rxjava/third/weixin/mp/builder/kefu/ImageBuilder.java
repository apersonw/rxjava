package org.rxjava.third.weixin.mp.builder.kefu;

import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.mp.bean.kefu.WxMpKefuMessage;

/**
 * 获得消息builder
 * 用法: WxMpKefuMessage m = WxMpKefuMessage.IMAGE().mediaId(...).toUser(...).build();
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder> {
    private String mediaId;

    public ImageBuilder() {
        this.msgType = WxConsts.KefuMsgType.IMAGE;
    }

    public ImageBuilder mediaId(String media_id) {
        this.mediaId = media_id;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
