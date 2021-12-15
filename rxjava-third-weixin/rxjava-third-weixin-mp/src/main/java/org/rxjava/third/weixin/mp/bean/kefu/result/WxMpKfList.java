package top.rxjava.third.weixin.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class WxMpKfList implements Serializable {
    private static final long serialVersionUID = -8194193505173564894L;

    @SerializedName("kf_list")
    private List<WxMpKfInfo> kfList;

    public static WxMpKfList fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpKfList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
