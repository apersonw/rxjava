package org.rxjava.third.tencent.weixin.mp.builder.outxml;

import org.rxjava.third.tencent.weixin.mp.bean.message.WxMpXmlOutImageMessage;

/**
 * 图片消息builder
 *
 * @author chanjarster
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder, WxMpXmlOutImageMessage> {

    private String mediaId;

    public ImageBuilder mediaId(String media_id) {
        this.mediaId = media_id;
        return this;
    }

    @Override
    public WxMpXmlOutImageMessage build() {
        WxMpXmlOutImageMessage m = new WxMpXmlOutImageMessage();
        setCommon(m);
        m.setMediaId(this.mediaId);
        return m;
    }

}
