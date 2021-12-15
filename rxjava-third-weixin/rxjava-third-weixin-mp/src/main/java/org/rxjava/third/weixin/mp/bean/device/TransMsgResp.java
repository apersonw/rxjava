package top.rxjava.third.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.common.util.json.WxGsonBuilder;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TransMsgResp extends AbstractDeviceBean {
    private static final long serialVersionUID = 5386954916622816491L;

    private Integer ret;
    @SerializedName("ret_info")
    private String retInfo;
    @SerializedName("errcode")
    private Integer errCode;
    @SerializedName("errmsg")
    private String errMsg;

    public static TransMsgResp fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, TransMsgResp.class);
    }
}
