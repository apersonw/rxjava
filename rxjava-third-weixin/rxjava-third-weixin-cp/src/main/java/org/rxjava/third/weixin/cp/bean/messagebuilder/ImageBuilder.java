package org.rxjava.third.weixin.cp.bean.messagebuilder;

import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.cp.bean.WxCpMessage;

/**
 * 获得消息builder
 * <p>
 * 用法: WxCustomMessage m = WxCustomMessage.IMAGE().mediaId(...).toUser(...).build();
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
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setMediaId(this.mediaId);
        return m;
    }
}
