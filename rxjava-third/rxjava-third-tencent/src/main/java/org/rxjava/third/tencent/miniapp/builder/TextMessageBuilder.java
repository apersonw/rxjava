package org.rxjava.third.tencent.miniapp.builder;

import org.rxjava.third.tencent.miniapp.bean.WxMaKefuMessage;

import static org.rxjava.third.tencent.miniapp.constant.WxMaConstants.KefuMsgType;

/**
 * 文本消息builder.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public final class TextMessageBuilder extends BaseBuilder<TextMessageBuilder> {
  private String content;

  public TextMessageBuilder() {
    this.msgType = KefuMsgType.TEXT;
  }

  public TextMessageBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public WxMaKefuMessage build() {
    WxMaKefuMessage m = super.build();
    m.setText(new WxMaKefuMessage.KfText(this.content));
    return m;
  }
}
