package top.rxjava.third.weixin.common.bean.menu;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import top.rxjava.third.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * menu button.
 */
@Data
public class WxMenuButton implements Serializable {
    private static final long serialVersionUID = -1070939403109776555L;

    /**
     * 菜单的响应动作类型.
     * view表示网页类型，
     * click表示点击类型，
     * miniprogram表示小程序类型
     */
    private String type;

    /**
     * 菜单标题，不超过16个字节，子菜单不超过60个字节.
     */
    private String name;

    /**
     * 菜单KEY值，用于消息接口推送，不超过128字节.
     * click等点击类型必须
     */
    private String key;

    /**
     * 网页链接.
     * 用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。
     * view、miniprogram类型必须
     */
    private String url;

    /**
     * 调用新增永久素材接口返回的合法media_id.
     * media_id类型和view_limited类型必须
     */
    @SerializedName("media_id")
    private String mediaId;

    /**
     * 小程序的appid.
     * miniprogram类型必须
     */
    @SerializedName("appid")
    private String appId;

    /**
     * 小程序的页面路径.
     * miniprogram类型必须
     */
    @SerializedName("pagepath")
    private String pagePath;

    @SerializedName("sub_button")
    private List<WxMenuButton> subButtons = new ArrayList<>();

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

}
