package org.rxjava.third.tencent.weixin.open.util.json;

import com.google.gson.*;
import org.rxjava.third.tencent.weixin.common.util.json.GsonHelper;
import org.rxjava.third.tencent.weixin.open.bean.WxOpenComponentAccessToken;

import java.lang.reflect.Type;

/**
 */
public class WxOpenComponentAccessTokenGsonAdapter implements JsonDeserializer<WxOpenComponentAccessToken> {
    @Override
    public WxOpenComponentAccessToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxOpenComponentAccessToken componentAccessToken = new WxOpenComponentAccessToken();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        componentAccessToken.setComponentAccessToken(GsonHelper.getString(jsonObject, "component_access_token"));
        componentAccessToken.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
        return componentAccessToken;
    }
}
