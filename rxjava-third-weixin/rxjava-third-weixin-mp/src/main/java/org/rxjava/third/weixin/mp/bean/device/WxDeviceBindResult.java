package top.rxjava.third.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceBindResult extends AbstractDeviceBean {
    private static final long serialVersionUID = 4687725146279339359L;

    @SerializedName("base_resp")
    private BaseResp baseResp;

    public static WxDeviceBindResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxDeviceBindResult.class);
    }

}
