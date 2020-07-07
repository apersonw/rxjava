package org.rxjava.third.weixin.miniapp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 小程序码.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxaCode extends AbstractWxMaQrcodeWrapper implements Serializable {
    private static final long serialVersionUID = 1287399621649210322L;

    private String path;

    @Builder.Default
    private int width = 430;

    @SerializedName("auto_color")
    @Builder.Default
    private boolean autoColor = true;

    @SerializedName("is_hyaline")
    @Builder.Default
    private boolean isHyaline = false;

    @SerializedName("line_color")
    @Builder.Default
    private WxMaCodeLineColor lineColor = new WxMaCodeLineColor("0", "0", "0");

    public static WxaCode fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxaCode.class);
    }

}
