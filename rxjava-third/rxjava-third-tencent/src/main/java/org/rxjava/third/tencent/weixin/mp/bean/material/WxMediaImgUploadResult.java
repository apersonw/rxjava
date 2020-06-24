package org.rxjava.third.tencent.weixin.mp.bean.material;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 */
@Data
public class WxMediaImgUploadResult implements Serializable {
    private static final long serialVersionUID = 1996392453428768829L;
    private String url;

    public static WxMediaImgUploadResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMediaImgUploadResult.class);
    }

}
