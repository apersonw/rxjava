package org.rxjava.third.tencent.weixin.mp.builder.outxml;

import org.rxjava.third.tencent.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * 文本消息builder
 */
public final class TextBuilder extends BaseBuilder<TextBuilder, WxMpXmlOutTextMessage> {
    private String content;

    public TextBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public WxMpXmlOutTextMessage build() {
        WxMpXmlOutTextMessage m = new WxMpXmlOutTextMessage();
        setCommon(m);
        m.setContent(this.content);
        return m;
    }
}
