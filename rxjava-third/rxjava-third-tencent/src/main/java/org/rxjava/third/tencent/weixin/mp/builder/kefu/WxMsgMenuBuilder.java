package org.rxjava.third.tencent.weixin.mp.builder.kefu;

import org.rxjava.third.tencent.weixin.common.api.WxConsts;
import org.rxjava.third.tencent.weixin.mp.bean.kefu.WxMpKefuMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 菜单消息builder
 * <p>
 * 用法:
 * WxMpKefuMessage m = WxMpKefuMessage.MSGMENU().addMenus(lists).headContent(headContent).tailContent(tailContent).toUser(...).build();
 *
 * @author billytomato
 */
public final class WxMsgMenuBuilder extends BaseBuilder<WxMsgMenuBuilder> {
    private List<WxMpKefuMessage.MsgMenu> msgMenus = new ArrayList<>();
    private String headContent;
    private String tailContent;


    public WxMsgMenuBuilder() {
        this.msgType = WxConsts.KefuMsgType.MSGMENU;
    }

    public WxMsgMenuBuilder addMenus(WxMpKefuMessage.MsgMenu... msgMenus) {
        Collections.addAll(this.msgMenus, msgMenus);
        return this;
    }

    public WxMsgMenuBuilder msgMenus(List<WxMpKefuMessage.MsgMenu> msgMenus) {
        this.msgMenus = msgMenus;
        return this;
    }

    public WxMsgMenuBuilder headContent(String headContent) {
        this.headContent = headContent;
        return this;
    }

    public WxMsgMenuBuilder tailContent(String tailContent) {
        this.tailContent = tailContent;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setHeadContent(this.headContent);
        m.setTailContent(this.tailContent);
        m.setMsgMenus(this.msgMenus);
        return m;
    }
}
