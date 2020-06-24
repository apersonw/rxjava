package org.rxjava.third.tencent.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceQrCodeResult extends AbstractDeviceBean {
    private static final long serialVersionUID = -4312858303060918266L;

    @SerializedName("deviceid")
    private String deviceId;
    @SerializedName("qrticket")
    private String qrTicket;
    @SerializedName("devicelicence")
    private String deviceLicence;
    @SerializedName("base_resp")
    private BaseResp baseResp;

    public static WxDeviceQrCodeResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxDeviceQrCodeResult.class);
    }

}
