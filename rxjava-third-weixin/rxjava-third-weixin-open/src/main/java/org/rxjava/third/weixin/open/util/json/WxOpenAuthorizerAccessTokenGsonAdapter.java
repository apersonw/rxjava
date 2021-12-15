package top.rxjava.third.weixin.open.util.json;

import com.google.gson.*;
import top.rxjava.third.weixin.common.util.json.GsonHelper;
import top.rxjava.third.weixin.open.bean.WxOpenAuthorizerAccessToken;

import java.lang.reflect.Type;

/**
 */
public class WxOpenAuthorizerAccessTokenGsonAdapter implements JsonDeserializer<WxOpenAuthorizerAccessToken> {
    @Override
    public WxOpenAuthorizerAccessToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxOpenAuthorizerAccessToken authorizerAccessToken = new WxOpenAuthorizerAccessToken();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        authorizerAccessToken.setAuthorizerAccessToken(GsonHelper.getString(jsonObject, "authorizer_access_token"));
        authorizerAccessToken.setExpiresIn(GsonHelper.getPrimitiveInteger(jsonObject, "expires_in"));
        return authorizerAccessToken;
    }
}
