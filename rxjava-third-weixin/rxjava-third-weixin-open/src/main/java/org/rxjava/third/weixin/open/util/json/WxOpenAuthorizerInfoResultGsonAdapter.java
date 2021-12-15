package top.rxjava.third.weixin.open.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import top.rxjava.third.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import top.rxjava.third.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import top.rxjava.third.weixin.open.bean.result.WxOpenAuthorizerInfoResult;

import java.lang.reflect.Type;

/**
 */
public class WxOpenAuthorizerInfoResultGsonAdapter implements JsonDeserializer<WxOpenAuthorizerInfoResult> {
    @Override
    public WxOpenAuthorizerInfoResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxOpenAuthorizerInfoResult authorizerInfoResult = new WxOpenAuthorizerInfoResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        WxOpenAuthorizationInfo authorizationInfo = WxOpenGsonBuilder.create().fromJson(jsonObject.get("authorization_info"),
                new TypeToken<WxOpenAuthorizationInfo>() {
                }.getType());

        authorizerInfoResult.setAuthorizationInfo(authorizationInfo);
        WxOpenAuthorizerInfo authorizerInfo = WxOpenGsonBuilder.create().fromJson(jsonObject.get("authorizer_info"),
                new TypeToken<WxOpenAuthorizerInfo>() {
                }.getType());

        authorizerInfoResult.setAuthorizerInfo(authorizerInfo);
        return authorizerInfoResult;
    }
}
