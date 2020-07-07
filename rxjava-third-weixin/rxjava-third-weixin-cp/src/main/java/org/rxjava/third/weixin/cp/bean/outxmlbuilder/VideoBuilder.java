package org.rxjava.third.weixin.cp.bean.outxmlbuilder;

import org.rxjava.third.weixin.cp.bean.WxCpXmlOutVideoMessage;

/**
 * 视频消息builder
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxCpXmlOutVideoMessage> {

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
    public WxCpXmlOutVideoMessage build() {
        WxCpXmlOutVideoMessage m = new WxCpXmlOutVideoMessage();
        setCommon(m);
        m.setTitle(this.title);
        m.setDescription(this.description);
        m.setMediaId(this.mediaId);
        return m;
    }

}
