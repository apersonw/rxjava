package org.rxjava.third.weixin.mp.bean;

import lombok.Data;
import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 */
@Data
public class WxMpMassPreviewMessage implements Serializable {
    private static final long serialVersionUID = 9095211638358424020L;

    private String toWxUserName;
    private String toWxUserOpenid;
    /**
     * 消息类型
     * 请使用
     * {@link WxConsts.MassMsgType#IMAGE}
     * {@link WxConsts.MassMsgType#MPNEWS}
     * {@link WxConsts.MassMsgType#TEXT}
     * {@link WxConsts.MassMsgType#MPVIDEO}
     * {@link WxConsts.MassMsgType#VOICE}
     * 如果msgtype和media_id不匹配的话，会返回系统繁忙的错误
     */
    private String msgType;
    private String content;
    private String mediaId;

    public WxMpMassPreviewMessage() {
        super();
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
