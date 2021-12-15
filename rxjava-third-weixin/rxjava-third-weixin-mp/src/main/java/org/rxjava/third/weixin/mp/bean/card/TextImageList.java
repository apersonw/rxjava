package top.rxjava.third.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 图文列表.
 */
@Data
public class TextImageList implements Serializable {

    /**
     * 图片链接,必须调用 上传图片接口 上传图片获得链接，并在此填入， 否则报错
     */
    @SerializedName("image_url")
    private String imageUrl;

    /**
     * 图文描述.
     */
    @SerializedName("text")
    private String text;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
