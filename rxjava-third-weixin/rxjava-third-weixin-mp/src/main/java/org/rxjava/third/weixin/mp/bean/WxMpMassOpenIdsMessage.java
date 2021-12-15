package top.rxjava.third.weixin.mp.bean;

import lombok.Data;
import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * openid列表群发的消息
 */
@Data
public class WxMpMassOpenIdsMessage implements Serializable {
    private static final long serialVersionUID = -8022910911104788999L;

    /**
     * openid列表，最多支持10,000个
     */
    private List<String> toUsers = new ArrayList<>();

    /**
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
    /**
     * 文章被判定为转载时，是否继续进行群发操作。
     */
    private boolean sendIgnoreReprint = false;

    /**
     * 开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
     */
    private String clientMsgId;

    public WxMpMassOpenIdsMessage() {
        super();
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    /**
     * 添加openid，最多支持10,000个
     */
    public void addUser(String openid) {
        this.toUsers.add(openid);
    }

}
