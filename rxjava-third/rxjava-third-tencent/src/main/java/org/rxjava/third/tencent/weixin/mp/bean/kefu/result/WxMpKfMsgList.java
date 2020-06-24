package org.rxjava.third.tencent.weixin.mp.bean.kefu.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Data
public class WxMpKfMsgList implements Serializable {
    private static final long serialVersionUID = 4524296707435188202L;

    @SerializedName("recordlist")
    private List<WxMpKfMsgRecord> records;

    @SerializedName("number")
    private Integer number;

    @SerializedName("msgid")
    private Long msgId;

    public static WxMpKfMsgList fromJson(String responseContent) {
        return WxMpGsonBuilder.create().fromJson(responseContent, WxMpKfMsgList.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
