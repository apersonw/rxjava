package top.rxjava.third.weixin.miniapp.builder;

import top.rxjava.third.weixin.miniapp.bean.WxMaKefuMessage;

import static top.rxjava.third.weixin.miniapp.constant.WxMaConstants.KefuMsgType;

/**
 * 图片消息builder.
 */
public final class ImageMessageBuilder extends BaseBuilder<ImageMessageBuilder> {
    private String mediaId;

    public ImageMessageBuilder() {
        this.msgType = KefuMsgType.IMAGE;
    }

    public ImageMessageBuilder mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    @Override
    public WxMaKefuMessage build() {
        WxMaKefuMessage m = super.build();
        m.setImage(new WxMaKefuMessage.KfImage(this.mediaId));
        return m;
    }
}
