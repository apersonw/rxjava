package org.rxjava.third.tencent.weixin.mp.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.rxjava.third.tencent.weixin.mp.bean.subscribe.WxMpSubscribeMessage;

import java.lang.reflect.Type;

/**
 */
public class WxMpSubscribeMessageGsonAdapter implements JsonSerializer<WxMpSubscribeMessage> {

    @Override
    public JsonElement serialize(WxMpSubscribeMessage message, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject messageJson = new JsonObject();
        messageJson.addProperty("touser", message.getToUser());
        messageJson.addProperty("template_id", message.getTemplateId());

        if (message.getUrl() != null) {
            messageJson.addProperty("url", message.getUrl());
        }

        final WxMpSubscribeMessage.MiniProgram miniProgram = message.getMiniProgram();
        if (miniProgram != null) {
            JsonObject miniProgramJson = new JsonObject();
            miniProgramJson.addProperty("appid", miniProgram.getAppid());
            if (miniProgram.isUsePath()) {
                miniProgramJson.addProperty("path", miniProgram.getPagePath());
            } else {
                miniProgramJson.addProperty("pagepath", miniProgram.getPagePath());
            }
            messageJson.add("miniprogram", miniProgramJson);
        }

        messageJson.addProperty("scene", message.getScene());
        messageJson.addProperty("title", message.getTitle());

        JsonObject data = new JsonObject();
        messageJson.add("data", data);

        JsonObject content = new JsonObject();
        data.add("content", content);

        if (message.getContentValue() != null) {
            content.addProperty("value", message.getContentValue());
        }

        if (message.getContentColor() != null) {
            content.addProperty("color", message.getContentColor());
        }

        return messageJson;

    }
}
