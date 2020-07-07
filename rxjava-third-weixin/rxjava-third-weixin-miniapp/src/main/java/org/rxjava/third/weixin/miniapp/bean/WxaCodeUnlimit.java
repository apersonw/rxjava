package org.rxjava.third.weixin.miniapp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 小程序码接口B.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxaCodeUnlimit extends AbstractWxMaQrcodeWrapper implements Serializable {
    private static final long serialVersionUID = 4782193774524960401L;
    private String scene;
    private String page;

    private int width = 430;

    @SerializedName("auto_color")
    private boolean autoColor = true;

    @SerializedName("is_hyaline")
    private boolean isHyaline = false;

    @SerializedName("line_color")
    private WxMaCodeLineColor lineColor = new WxMaCodeLineColor("0", "0", "0");

    public static WxaCodeUnlimit fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxaCodeUnlimit.class);
    }

}
