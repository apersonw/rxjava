package org.rxjava.third.tencent.weixin.mp.builder.kefu;

import org.rxjava.third.tencent.weixin.common.api.WxConsts;
import org.rxjava.third.tencent.weixin.mp.bean.kefu.WxMpKefuMessage;

/**
 * 文本消息builder
 * 用法: WxMpKefuMessage m = WxMpKefuMessage.TEXT().content(...).toUser(...).build();
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
    private String content;

    public TextBuilder() {
        this.msgType = WxConsts.KefuMsgType.TEXT;
    }

    public TextBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setContent(this.content);
        return m;
    }
}
