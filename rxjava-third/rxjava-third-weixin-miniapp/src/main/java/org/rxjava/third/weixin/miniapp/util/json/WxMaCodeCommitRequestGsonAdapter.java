package org.rxjava.third.weixin.miniapp.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.rxjava.third.weixin.miniapp.bean.code.WxMaCodeCommitRequest;

import java.lang.reflect.Type;

/**
 *
 */
public class WxMaCodeCommitRequestGsonAdapter implements JsonSerializer<WxMaCodeCommitRequest> {

    @Override
    public JsonElement serialize(WxMaCodeCommitRequest request, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("template_id", request.getTemplateId());
        requestJson.addProperty("user_version", request.getUserVersion());
        requestJson.addProperty("user_desc", request.getUserDesc());
        if (request.getExtConfig() != null) {
            requestJson.addProperty("ext_json", WxMaGsonBuilder.create().toJson(request.getExtConfig()));
        }
        return requestJson;
    }
}
