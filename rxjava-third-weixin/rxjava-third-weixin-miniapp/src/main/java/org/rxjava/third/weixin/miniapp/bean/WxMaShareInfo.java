package top.rxjava.third.weixin.miniapp.bean;

import lombok.Data;
import top.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 */
@Data
public class WxMaShareInfo implements Serializable {
    private static final long serialVersionUID = -8053613683499632226L;

    private String openGId;

    public static WxMaShareInfo fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaShareInfo.class);
    }
}
