package org.rxjava.third.weixin.miniapp.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.rxjava.third.weixin.miniapp.bean.WxMaSubscribeMessage;

import java.lang.reflect.Type;

/**
 */
public class WxMaSubscribeMessageGsonAdapter implements JsonSerializer<WxMaSubscribeMessage> {
    @Override
    public JsonElement serialize(WxMaSubscribeMessage message, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject messageJson = new JsonObject();
        messageJson.addProperty("touser", message.getToUser());
        messageJson.addProperty("template_id", message.getTemplateId());
        if (message.getPage() != null) {
            messageJson.addProperty("page", message.getPage());
        }

        if (message.getMiniprogramState() != null) {
            messageJson.addProperty("miniprogram_state", message.getMiniprogramState());
        }

        if (message.getLang() != null) {
            messageJson.addProperty("lang", message.getLang());
        }

        JsonObject data = new JsonObject();
        messageJson.add("data", data);

        if (message.getData() == null) {
            return messageJson;
        }

        for (WxMaSubscribeMessage.Data datum : message.getData()) {
            JsonObject dataJson = new JsonObject();
            dataJson.addProperty("value", datum.getValue());
            data.add(datum.getName(), dataJson);
        }

        return messageJson;
    }

}
