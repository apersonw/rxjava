package top.rxjava.third.weixin.cp.bean.messagebuilder;

import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.cp.bean.WxCpMessage;

/**
 * markdown类型的消息builder
 */
public class MarkdownMsgBuilder extends BaseBuilder<MarkdownMsgBuilder> {
    private String content;

    public MarkdownMsgBuilder() {
        this.msgType = WxConsts.KefuMsgType.MARKDOWN;
    }

    public MarkdownMsgBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setContent(this.content);
        return m;
    }
}
