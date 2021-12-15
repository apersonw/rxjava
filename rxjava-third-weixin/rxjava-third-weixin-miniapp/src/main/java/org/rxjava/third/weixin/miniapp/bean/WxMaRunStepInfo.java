package top.rxjava.third.weixin.miniapp.bean;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import top.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 微信运动步数信息.
 */
@Data
public class WxMaRunStepInfo implements Serializable {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final long serialVersionUID = -7496372171398607044L;

    /**
     * 时间戳，表示数据对应的时间.
     */
    private Long timestamp;

    /**
     * 微信运动步数.
     */
    private Integer step;

    public static List<WxMaRunStepInfo> fromJson(String json) {
        JsonObject jsonObject = JSON_PARSER.parse(json).getAsJsonObject();
        return WxMaGsonBuilder.create().fromJson(jsonObject.get("stepInfoList").toString(),
                new TypeToken<List<WxMaRunStepInfo>>() {
                }.getType());
    }
}
