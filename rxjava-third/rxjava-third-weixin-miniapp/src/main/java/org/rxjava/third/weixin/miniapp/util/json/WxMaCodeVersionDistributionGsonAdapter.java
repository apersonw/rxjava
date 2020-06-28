package org.rxjava.third.weixin.miniapp.util.json;

import com.google.gson.*;
import org.rxjava.third.weixin.common.util.json.GsonHelper;
import org.rxjava.third.weixin.miniapp.bean.code.WxMaCodeVersionDistribution;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class WxMaCodeVersionDistributionGsonAdapter implements JsonDeserializer<WxMaCodeVersionDistribution> {
    @Override
    public WxMaCodeVersionDistribution deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (json == null) {
            return null;
        }

        WxMaCodeVersionDistribution distribution = new WxMaCodeVersionDistribution();
        JsonObject object = json.getAsJsonObject();
        distribution.setNowVersion(GsonHelper.getString(object, "now_version"));
        distribution.setUvInfo(getAsMap(object.getAsJsonObject("uv_info"), "items"));
        return distribution;
    }

    private Map<String, Float> getAsMap(JsonObject object, String memberName) {
        JsonArray array = object.getAsJsonArray(memberName);
        if (array != null && array.size() > 0) {
            Map<String, Float> map = new LinkedHashMap<>(array.size());
            for (JsonElement element : array) {
                JsonObject elementObject = element.getAsJsonObject();
                String version = GsonHelper.getString(elementObject, "version");
                if (version != null) {
                    Float percentage = GsonHelper.getFloat(elementObject, "percentage");
                    map.put(version, percentage);
                }
            }
            return map;
        }
        return null;
    }
}
