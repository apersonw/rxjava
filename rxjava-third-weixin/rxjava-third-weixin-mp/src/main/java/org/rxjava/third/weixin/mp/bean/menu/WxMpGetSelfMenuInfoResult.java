package org.rxjava.third.weixin.mp.bean.menu;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.weixin.common.util.json.WxGsonBuilder;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 */
@Data
public class WxMpGetSelfMenuInfoResult implements Serializable {
    private static final long serialVersionUID = -5612495636936835166L;

    @SerializedName("selfmenu_info")
    private WxMpSelfMenuInfo selfMenuInfo;

    @SerializedName("is_menu_open")
    private Integer isMenuOpen;

    public static WxMpGetSelfMenuInfoResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMpGetSelfMenuInfoResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
