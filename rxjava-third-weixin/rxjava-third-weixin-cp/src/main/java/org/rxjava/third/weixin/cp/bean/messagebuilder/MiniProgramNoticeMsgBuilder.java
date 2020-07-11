package org.rxjava.third.weixin.cp.bean.messagebuilder;

import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.cp.bean.WxCpMessage;

import java.util.Map;

/**
 * miniprogram_notice 类型的消息 builder
 */
public class MiniProgramNoticeMsgBuilder extends BaseBuilder<MiniProgramNoticeMsgBuilder> {
    private String title;
    private String description;
    private String appId;
    private String page;
    private Boolean emphasisFirstItem;
    private Map<String, String> contentItems;

    public MiniProgramNoticeMsgBuilder() {
        this.msgType = WxConsts.KefuMsgType.MINIPROGRAM_NOTICE;
    }

    public MiniProgramNoticeMsgBuilder appId(String appId) {
        this.appId = appId;
        return this;
    }

    public MiniProgramNoticeMsgBuilder page(String page) {
        this.page = page;
        return this;
    }

    public MiniProgramNoticeMsgBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MiniProgramNoticeMsgBuilder description(String description) {
        this.description = description;
        return this;
    }

    public MiniProgramNoticeMsgBuilder contentItems(Map<String, String> contentItems) {
        this.contentItems = contentItems;
        return this;
    }

    public MiniProgramNoticeMsgBuilder emphasisFirstItem(Boolean emphasisFirstItem) {
        this.emphasisFirstItem = emphasisFirstItem;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setContentItems(this.contentItems);
        m.setAppId(this.appId);
        m.setDescription(this.description);
        m.setTitle(this.title);
        m.setEmphasisFirstItem(this.emphasisFirstItem);
        m.setPage(this.page);
        return m;
    }
}