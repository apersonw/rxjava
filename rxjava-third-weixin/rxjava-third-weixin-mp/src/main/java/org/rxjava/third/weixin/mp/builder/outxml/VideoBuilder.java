package org.rxjava.third.weixin.mp.builder.outxml;

import org.rxjava.third.weixin.mp.bean.message.WxMpXmlOutVideoMessage;

/**
 * 视频消息builder
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxMpXmlOutVideoMessage> {

    private String mediaId;
    private String title;
    private String description;

    public VideoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public VideoBuilder description(String description) {
        this.description = description;
        return this;
    }

    public VideoBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxMpXmlOutVideoMessage build() {
        WxMpXmlOutVideoMessage m = new WxMpXmlOutVideoMessage();
        setCommon(m);
        m.getVideo().setTitle(this.title);
        m.getVideo().setDescription(this.description);
        m.getVideo().setMediaId(this.mediaId);
        return m;
    }

}
