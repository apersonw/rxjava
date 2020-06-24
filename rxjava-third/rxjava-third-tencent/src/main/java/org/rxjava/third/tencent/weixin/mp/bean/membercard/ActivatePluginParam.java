package org.rxjava.third.tencent.weixin.mp.bean.membercard;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 */
@Data
public class ActivatePluginParam {

    @SerializedName("encrypt_card_id")
    String encryptCardId;

    @SerializedName("outer_str")
    String outerStr;

    @SerializedName("biz")
    String biz;

}
