package top.rxjava.third.weixin.mp.bean.kefu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.rxjava.third.weixin.common.api.WxConsts;
import top.rxjava.third.weixin.mp.builder.kefu.*;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 客服消息.
 */
@Data
public class WxMpKefuMessage implements Serializable {
    private static final long serialVersionUID = -9196732086954365246L;

    private String toUser;
    private String msgType;
    private String content;
    private String mediaId;
    private String thumbMediaId;
    private String title;
    private String description;
    private String musicUrl;
    private String hqMusicUrl;
    private String kfAccount;
    private String cardId;
    private String mpNewsMediaId;
    private String miniProgramAppId;
    private String miniProgramPagePath;
    private String headContent;
    private String tailContent;
    private List<WxArticle> articles = new ArrayList<>();

    /**
     * 菜单消息里的菜单内容.
     */
    private List<MsgMenu> msgMenus = new ArrayList<>();

    /**
     * 获得文本消息builder.
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得图片消息builder.
     */
    public static ImageBuilder IMAGE() {
        return new ImageBuilder();
    }

    /**
     * 获得语音消息builder.
     */
    public static VoiceBuilder VOICE() {
        return new VoiceBuilder();
    }

    /**
     * 获得视频消息builder.
     */
    public static VideoBuilder VIDEO() {
        return new VideoBuilder();
    }

    /**
     * 获得音乐消息builder.
     */
    public static MusicBuilder MUSIC() {
        return new MusicBuilder();
    }

    /**
     * 获得图文消息（点击跳转到外链）builder.
     */
    public static NewsBuilder NEWS() {
        return new NewsBuilder();
    }

    /**
     * 获得图文消息（点击跳转到图文消息页面）builder.
     */
    public static MpNewsBuilder MPNEWS() {
        return new MpNewsBuilder();
    }

    /**
     * 获得卡券消息builder.
     */
    public static WxCardBuilder WXCARD() {
        return new WxCardBuilder();
    }

    /**
     * 获得菜单消息builder.
     */
    public static WxMsgMenuBuilder MSGMENU() {
        return new WxMsgMenuBuilder();
    }

    /**
     * 小程序卡片.
     */
    public static MiniProgramPageBuilder MINIPROGRAMPAGE() {
        return new MiniProgramPageBuilder();
    }

    /**
     * 请使用
     * {@link WxConsts.KefuMsgType#TEXT}
     * {@link WxConsts.KefuMsgType#IMAGE}
     * {@link WxConsts.KefuMsgType#VOICE}
     * {@link WxConsts.KefuMsgType#MUSIC}
     * {@link WxConsts.KefuMsgType#VIDEO}
     * {@link WxConsts.KefuMsgType#NEWS}
     * {@link WxConsts.KefuMsgType#MPNEWS}
     * {@link WxConsts.KefuMsgType#WXCARD}
     * {@link WxConsts.KefuMsgType#MINIPROGRAMPAGE}
     * {@link WxConsts.KefuMsgType#TASKCARD}
     * {@link WxConsts.KefuMsgType#MSGMENU}
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WxArticle implements Serializable {
        private static final long serialVersionUID = 5145137235440507379L;

        private String title;
        private String description;
        private String url;
        private String picUrl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MsgMenu implements Serializable {
        private static final long serialVersionUID = 7020769047598378839L;

        private String id;
        private String content;
    }
}
