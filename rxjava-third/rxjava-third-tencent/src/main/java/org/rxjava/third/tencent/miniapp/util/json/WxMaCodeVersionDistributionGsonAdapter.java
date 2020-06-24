package org.rxjava.third.tencent.miniapp.util.json;

import org.rxjava.third.tencent.miniapp.bean.code.WxMaCodeVersionDistribution;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.rxjava.third.tencent.common.util.json.GsonHelper;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/charmingoh">Charming</a>
 * @since 2018-04-26 19:47
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
