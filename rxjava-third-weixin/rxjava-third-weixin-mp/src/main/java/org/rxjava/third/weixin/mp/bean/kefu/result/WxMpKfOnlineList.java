package org.rxjava.third.weixin.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
public class WxMpKfOnlineList implements Serializable {
    private static final long serialVersionUID = -6154705288500854617L;

    @SerializedName("kf_online_list")
    private List<WxMpKfInfo> kfOnlineList;

    public static WxMpKfOnlineList fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpKfOnlineList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
