package top.rxjava.third.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseResp extends AbstractDeviceBean {
    private static final long serialVersionUID = 4252655933699659073L;

    @SerializedName("base_info")
    private BaseInfo baseInfo;
    @SerializedName("errcode")
    private Integer errCode;
    @SerializedName("errmsg")
    private String errMsg;

    @Data
    private class BaseInfo {
        @SerializedName("device_type")
        private String deviceType;

        @SerializedName("device_id")
        private String deviceId;
    }
}
