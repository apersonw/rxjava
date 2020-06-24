package org.rxjava.third.tencent.weixin.miniapp.util.json;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.tencent.weixin.common.util.json.GsonHelper;
import org.rxjava.third.tencent.weixin.miniapp.bean.analysis.WxMaUserPortrait;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/charmingoh">Charming</a>
 * @since 2018-04-28
 */
public class WxMaUserPortraitGsonAdapter implements JsonDeserializer<WxMaUserPortrait> {
    @Override
    public WxMaUserPortrait deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (json == null) {
            return null;
        }

        WxMaUserPortrait portrait = new WxMaUserPortrait();
        JsonObject object = json.getAsJsonObject();
        String refDate = GsonHelper.getString(object, "ref_date");
        portrait.setRefDate(refDate);
        portrait.setVisitUvNew(getPortraitItem(object.getAsJsonObject("visit_uv_new")));
        portrait.setVisitUv(getPortraitItem(object.getAsJsonObject("visit_uv")));
        return portrait;
    }

    private WxMaUserPortrait.Item getPortraitItem(JsonObject object) {
        if (object == null) {
            return null;
        }
        WxMaUserPortrait.Item item = new WxMaUserPortrait.Item();
        item.setProvince(getAsMap(object, "province"));
        item.setCity(getAsMap(object, "city"));
        item.setGenders(getAsMap(object, "genders"));
        item.setPlatforms(getAsMap(object, "platforms"));
        item.setDevices(getAsMap(object, "devices"));
        item.setAges(getAsMap(object, "ages"));
        return item;
    }

    private Map<String, Long> getAsMap(JsonObject object, String memberName) {
        JsonArray array = object.getAsJsonArray(memberName);
        if (array != null && array.size() > 0) {
            Map<String, Long> map = new LinkedHashMap<>(array.size());
            for (JsonElement element : array) {
                JsonObject elementObject = element.getAsJsonObject();
                String name = GsonHelper.getString(elementObject, "name");
                if (StringUtils.isNotBlank(name)) {
                    Long value = GsonHelper.getLong(elementObject, "value");
                    map.put(name, value);
                }
            }
            return map;
        }
        return null;
    }
}
