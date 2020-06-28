package org.rxjava.third.weixin.mp.builder.kefu;

import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.mp.bean.kefu.WxMpKefuMessage;

/**
 * 音乐消息builder
 * <p>
 * 用法: WxMpKefuMessage m = WxMpKefuMessage.MUSIC()
 * .musicUrl(...)
 * .hqMusicUrl(...)
 * .title(...)
 * .thumbMediaId(..)
 * .description(..)
 * .toUser(...)
 * .build();
 */
public final class MusicBuilder extends BaseBuilder<MusicBuilder> {
    private String title;
    private String description;
    private String thumbMediaId;
    private String musicUrl;
    private String hqMusicUrl;

    public MusicBuilder() {
        this.msgType = WxConsts.KefuMsgType.MUSIC;
    }

    public MusicBuilder musicUrl(String musicurl) {
        this.musicUrl = musicurl;
        return this;
    }

    public MusicBuilder hqMusicUrl(String hqMusicurl) {
        this.hqMusicUrl = hqMusicurl;
        return this;
    }

    public MusicBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MusicBuilder description(String description) {
        this.description = description;
        return this;
    }

    public MusicBuilder thumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
        return this;
    }

    @Override
    public WxMpKefuMessage build() {
        WxMpKefuMessage m = super.build();
        m.setMusicUrl(this.musicUrl);
        m.setHqMusicUrl(this.hqMusicUrl);
        m.setTitle(this.title);
        m.setDescription(this.description);
        m.setThumbMediaId(this.thumbMediaId);
        return m;
    }
}
