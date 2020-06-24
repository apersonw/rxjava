package org.rxjava.third.tencent.weixin.open.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.rxjava.third.tencent.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import org.rxjava.third.tencent.weixin.open.bean.result.WxOpenQueryAuthResult;

import java.lang.reflect.Type;

/**
 */
public class WxOpenQueryAuthResultGsonAdapter implements JsonDeserializer<WxOpenQueryAuthResult> {
    @Override
    public WxOpenQueryAuthResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxOpenQueryAuthResult queryAuthResult = new WxOpenQueryAuthResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        WxOpenAuthorizationInfo authorizationInfo = WxOpenGsonBuilder.create().fromJson(jsonObject.get("authorization_info"),
                new TypeToken<WxOpenAuthorizationInfo>() {
                }.getType());

        queryAuthResult.setAuthorizationInfo(authorizationInfo);
        return queryAuthResult;
    }
}
