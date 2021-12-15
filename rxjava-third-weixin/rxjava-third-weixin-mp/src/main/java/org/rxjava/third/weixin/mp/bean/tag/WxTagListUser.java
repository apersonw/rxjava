package top.rxjava.third.weixin.mp.bean.tag;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 获取标签下粉丝列表的结果对象
 */
@Data
public class WxTagListUser implements Serializable {
    private static final long serialVersionUID = -4551768374200676112L;

    /**
     * "count":2,这次获取的粉丝数量.
     */
    @SerializedName("count")
    private Integer count;
    /**
     * "data" 粉丝列表.
     */
    @SerializedName("data")
    private WxTagListUserData data;
    /**
     * "next_openid" 拉取列表最后一个用户的openid.
     */
    @SerializedName("next_openid")
    private String nextOpenid;

    public static WxTagListUser fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxTagListUser.class);
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    @Data
    public static class WxTagListUserData implements Serializable {
        private static final long serialVersionUID = -8584537400336245701L;

        /**
         * openid 列表.
         */
        @SerializedName("openid")
        private List<String> openidList;

        @Override
        public String toString() {
            return WxMpGsonBuilder.create().toJson(this);
        }
    }
}
