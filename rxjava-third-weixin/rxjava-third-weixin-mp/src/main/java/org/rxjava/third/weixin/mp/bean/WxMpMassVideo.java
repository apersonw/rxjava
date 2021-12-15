package top.rxjava.third.weixin.mp.bean;

import lombok.Data;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 群发时用到的视频素材
 */
@Data
public class WxMpMassVideo implements Serializable {
    private static final long serialVersionUID = 9153925016061915637L;

    private String mediaId;
    private String title;
    private String description;

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
