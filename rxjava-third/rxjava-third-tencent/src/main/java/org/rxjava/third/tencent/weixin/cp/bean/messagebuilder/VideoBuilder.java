package org.rxjava.third.tencent.weixin.cp.bean.messagebuilder;

import org.rxjava.third.tencent.weixin.common.api.WxConsts;
import org.rxjava.third.tencent.weixin.cp.bean.WxCpMessage;

/**
 * 视频消息builder
 * <p>
 * 用法: WxCustomMessage m = WxCustomMessage.VOICE()
 * .mediaId(...)
 * .title(...)
 * .thumbMediaId(..)
 * .description(..)
 * .toUser(...)
 * .build();
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder> {
    private String mediaId;
    private String title;
    private String description;
    private String thumbMediaId;

    public VideoBuilder() {
        this.msgType = WxConsts.KefuMsgType.VIDEO;
    }

    public VideoBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public VideoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public VideoBuilder description(String description) {
        this.description = description;
        return this;
    }

    public VideoBuilder thumbMediaId(String thumb_media_id) {
        this.thumbMediaId = thumb_media_id;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setMediaId(this.mediaId);
        m.setTitle(this.title);
        m.setDescription(this.description);
        m.setThumbMediaId(this.thumbMediaId);
        return m;
    }
}
