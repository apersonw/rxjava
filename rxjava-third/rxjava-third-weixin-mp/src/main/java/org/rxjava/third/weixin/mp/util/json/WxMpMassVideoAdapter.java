package org.rxjava.third.weixin.mp.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.rxjava.third.weixin.mp.bean.WxMpMassVideo;

import java.lang.reflect.Type;

/**
 *
 */
public class WxMpMassVideoAdapter implements JsonSerializer<WxMpMassVideo> {

    @Override
    public JsonElement serialize(WxMpMassVideo message, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject messageJson = new JsonObject();
        messageJson.addProperty("media_id", message.getMediaId());
        messageJson.addProperty("description", message.getDescription());
        messageJson.addProperty("title", message.getTitle());
        return messageJson;
    }

}
