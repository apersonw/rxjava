package org.rxjava.third.tencent.weixin.common.util.json;

import com.google.gson.*;
import org.rxjava.third.tencent.weixin.common.bean.result.WxMediaUploadResult;

import java.lang.reflect.Type;

/**
 *
 */
public class WxMediaUploadResultAdapter implements JsonDeserializer<WxMediaUploadResult> {

    @Override
    public WxMediaUploadResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxMediaUploadResult result = new WxMediaUploadResult();
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.get("url") != null && !jsonObject.get("url").isJsonNull()) {
            result.setUrl(GsonHelper.getAsString(jsonObject.get("url")));
        }

        if (jsonObject.get("type") != null && !jsonObject.get("type").isJsonNull()) {
            result.setType(GsonHelper.getAsString(jsonObject.get("type")));
        }
        if (jsonObject.get("media_id") != null && !jsonObject.get("media_id").isJsonNull()) {
            result.setMediaId(GsonHelper.getAsString(jsonObject.get("media_id")));
        }
        if (jsonObject.get("thumb_media_id") != null && !jsonObject.get("thumb_media_id").isJsonNull()) {
            result.setThumbMediaId(GsonHelper.getAsString(jsonObject.get("thumb_media_id")));
        }
        if (jsonObject.get("created_at") != null && !jsonObject.get("created_at").isJsonNull()) {
            result.setCreatedAt(GsonHelper.getAsPrimitiveLong(jsonObject.get("created_at")));
        }
        return result;
    }

}
