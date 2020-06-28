package org.rxjava.third.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.util.List;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceOpenIdResult extends AbstractDeviceBean {
    private static final long serialVersionUID = 4980885167833836220L;

    @SerializedName("errcode")
    private Integer errCode;
    @SerializedName("errmsg")
    private String errMsg;
    @SerializedName("open_id")
    private List<String> openIds;
    @SerializedName("resp_msg")
    private RespMsg respMsg;

    public static WxDeviceOpenIdResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxDeviceOpenIdResult.class);
    }

}
