package top.rxjava.third.weixin.open.util.json;

import com.google.gson.*;
import top.rxjava.third.weixin.common.util.json.GsonHelper;
import top.rxjava.third.weixin.open.bean.result.WxOpenAuthorizerListResult;

import java.lang.reflect.Type;
import java.util.*;

/**
 */
public class WxOpenAuthorizerListResultGsonAdapter implements JsonDeserializer<WxOpenAuthorizerListResult> {

    private static final String AUTHORIZER_APPID = "authorizer_appid";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String AUTH_TIME = "auth_time";

    @Override
    public WxOpenAuthorizerListResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        WxOpenAuthorizerListResult wxOpenAuthorizerListResult = new WxOpenAuthorizerListResult();
        wxOpenAuthorizerListResult.setTotalCount(GsonHelper.getInteger(jsonObject, "total_count").intValue());

        List<Map<String, String>> list = new ArrayList<>();
        Iterator<JsonElement> jsonElementIterator = jsonObject.getAsJsonArray("list").iterator();

        while (jsonElementIterator.hasNext()) {
            JsonObject authorizer = jsonElementIterator.next().getAsJsonObject();
            Map<String, String> authorizerMap = new HashMap<>(10);

            authorizerMap.put(AUTHORIZER_APPID, GsonHelper.getString(authorizer, AUTHORIZER_APPID));
            authorizerMap.put(REFRESH_TOKEN, GsonHelper.getString(authorizer, REFRESH_TOKEN));
            authorizerMap.put(AUTH_TIME, GsonHelper.getString(authorizer, AUTH_TIME));
            list.add(authorizerMap);
        }
        wxOpenAuthorizerListResult.setList(list);
        return wxOpenAuthorizerListResult;
    }
}
