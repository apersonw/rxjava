package org.rxjava.third.tencent.weixin.mp.bean.material;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 图文消息article.
 * 1. thumbMediaId  (必填) 图文消息的封面图片素材id（必须是永久mediaID）
 * 2. author          图文消息的作者
 * 3. title           (必填) 图文消息的标题
 * 4. contentSourceUrl 在图文消息页面点击“阅读原文”后的页面链接
 * 5. content (必填)  图文消息页面的内容，支持HTML标签
 * 6. digest          图文消息的描述
 * 7. showCoverPic  是否显示封面，true为显示，false为不显示
 * 8. url           点击图文消息跳转链接
 * 9. need_open_comment（新增字段） 否 Uint32 是否打开评论，0不打开，1打开
 * 10. only_fans_can_comment（新增字段） 否 Uint32 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
 */
@Data
public class WxMpNewsArticle implements Serializable {
    private static final long serialVersionUID = -635384661692321171L;
    /**
     * (必填) 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得.
     */
    private String thumbMediaId;
    /**
     * 图文消息的封面url.
     */
    private String thumbUrl;
    /**
     * 图文消息的作者.
     */
    private String author;
    /**
     * (必填) 图文消息的标题.
     */
    private String title;
    /**
     * 在图文消息页面点击“阅读原文”后的页面链接.
     */
    private String contentSourceUrl;
    /**
     * (必填) 图文消息页面的内容，支持HTML标签.
     */
    private String content;
    /**
     * 图文消息的描述.
     */
    private String digest;
    /**
     * 是否显示封面，true为显示，false为不显示.
     */
    private boolean showCoverPic;

    /**
     * 点击图文消息跳转链接.
     */
    private String url;

    /**
     * need_open_comment
     * 是否打开评论，0不打开，1打开.
     */
    private Boolean needOpenComment;

    /**
     * only_fans_can_comment
     * 是否粉丝才可评论，0所有人可评论，1粉丝才可评论.
     */
    private Boolean onlyFansCanComment;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
