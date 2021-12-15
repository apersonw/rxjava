package top.rxjava.third.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.util.List;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceBindDeviceResult extends AbstractDeviceBean {
    private static final long serialVersionUID = 725870295905935355L;

    @SerializedName("resp_msg")
    private RespMsg respMsg;
    @SerializedName("openid")
    private String openId;
    @SerializedName("device_list")
    private List<Device> devices;

    public static WxDeviceBindDeviceResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxDeviceBindDeviceResult.class);
    }

    @Data
    public static class Device {
        @SerializedName("device_type")
        private String deviceType;

        @SerializedName("device_id")
        private String deviceId;
    }

}
