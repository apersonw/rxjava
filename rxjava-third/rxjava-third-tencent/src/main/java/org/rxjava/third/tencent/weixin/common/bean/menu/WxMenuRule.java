package org.rxjava.third.tencent.weixin.common.bean.menu;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * menu rule.
 */
@Data
public class WxMenuRule implements Serializable {
    private static final long serialVersionUID = -4587181819499286670L;

    /**
     * 变态的微信接口，反序列化时这里反人类的使用和序列化时不一样的名字.
     */
    @SerializedName(value = "tag_id", alternate = "group_id")
    private String tagId;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String clientPlatformType;
    private String language;

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }
}
