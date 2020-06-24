package org.rxjava.third.tencent.miniapp.bean;

import org.rxjava.third.tencent.miniapp.builder.ImageMessageBuilder;
import org.rxjava.third.tencent.miniapp.builder.LinkMessageBuilder;
import org.rxjava.third.tencent.miniapp.builder.MaPageMessageBuilder;
import org.rxjava.third.tencent.miniapp.builder.TextMessageBuilder;
import org.rxjava.third.tencent.miniapp.util.json.WxMaGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 客服消息.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public class WxMaKefuMessage implements Serializable {
  private static final long serialVersionUID = -9196732086954365246L;

  @SerializedName("touser")
  private String toUser;

  @SerializedName("msgtype")
  private String msgType;

  @SerializedName("text")
  private KfText text;

  @SerializedName("image")
  private KfImage image;

  @SerializedName("link")
  private KfLink link;

  @SerializedName("miniprogrampage")
  private KfMaPage maPage;

  @Data
  @AllArgsConstructor
  public static class KfText implements Serializable {
    private static final long serialVersionUID = 151122958720941270L;

    private String content;
  }

  @Data
  @AllArgsConstructor
  public static class KfImage implements Serializable {
    private static final long serialVersionUID = -5409342945117300782L;

    @SerializedName("media_id")
    private String mediaId;
  }

  @Data
  @Builder
  public static class KfLink implements Serializable {
    private static final long serialVersionUID = -6728776817556127413L;

    private String title;
    private String description;
    private String url;

    @SerializedName("thumb_url")
    private String thumbUrl;
  }

  @Data
  @Builder
  public static class KfMaPage implements Serializable {
    private static final long serialVersionUID = -5633492281871634466L;

    private String title;

    @SerializedName("pagepath")
    private String pagePath;

    @SerializedName("thumb_media_id")
    private String thumbMediaId;
  }

  /**
   * 获得文本消息builder.
   */
  public static TextMessageBuilder newTextBuilder() {
    return new TextMessageBuilder();
  }

  /**
   * 获得图片消息builder.
   */
  public static ImageMessageBuilder newImageBuilder() {
    return new ImageMessageBuilder();
  }

  /**
   * 获得图文链接消息builder.
   */
  public static LinkMessageBuilder newLinkBuilder() {
    return new LinkMessageBuilder();
  }

  /**
   * 获得图文链接消息builder.
   */
  public static MaPageMessageBuilder newMaPageBuilder() {
    return new MaPageMessageBuilder();
  }

  public String toJson() {
    return WxMaGsonBuilder.create().toJson(this);
  }

}
