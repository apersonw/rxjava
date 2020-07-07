package org.rxjava.third.weixin.mp.builder.kefu;

import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.mp.bean.kefu.WxMpKefuMessage;

/**
 * 卡券消息builder
 * 用法: WxMpKefuMessage m = WxMpKefuMessage.WXCARD().cardId(...).toUser(...).build();
 */
public final class WxCardBuilder extends BaseBuilder<WxCardBuilder> {
    private String cardId;

    public WxCardBuilder() {
        this.msgType = WxConsts.KefuMsgType.WXCARD;
    }

    public WxCardBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setCardId(this.cardId);
        return m;
    }
}
