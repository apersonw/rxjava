package org.rxjava.third.tencent.weixin.miniapp.util.json;

import com.google.gson.*;
import org.rxjava.third.tencent.weixin.common.util.json.GsonHelper;
import org.rxjava.third.tencent.weixin.miniapp.bean.analysis.WxMaRetainInfo;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class WxMaRetainInfoGsonAdapter implements JsonDeserializer<WxMaRetainInfo> {
    @Override
    public WxMaRetainInfo deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (json == null) {
            return null;
        }

        WxMaRetainInfo retainInfo = new WxMaRetainInfo();
        JsonObject object = json.getAsJsonObject();
        String refDate = GsonHelper.getString(object, "ref_date");
        retainInfo.setRefDate(refDate);
        retainInfo.setVisitUvNew(getAsMap(object, "visit_uv_new"));
        retainInfo.setVisitUv(getAsMap(object, "visit_uv"));
        return retainInfo;
    }

    private Map<Integer, Integer> getAsMap(JsonObject object, String memberName) {
        JsonArray array = object.getAsJsonArray(memberName);
        if (array != null && array.size() > 0) {
            Map<Integer, Integer> map = new LinkedHashMap<>(array.size());
            for (JsonElement element : array) {
                JsonObject elementObject = element.getAsJsonObject();
                Integer key = GsonHelper.getInteger(elementObject, "key");
                if (key != null) {
                    Integer value = GsonHelper.getInteger(elementObject, "value");
                    map.put(key, value);
                }
            }
            return map;
        }
        return null;
    }
}
