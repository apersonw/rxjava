package org.rxjava.third.tencent.weixin.mp.bean.result;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 上传群发用的素材的结果.
 * 视频和图文消息需要在群发前上传素材
 */
@Data
public class WxMpMassUploadResult implements Serializable {
    private static final long serialVersionUID = 6568157943644994029L;

    private String type;
    private String mediaId;
    private long createdAt;

    public static WxMpMassUploadResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMassUploadResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
