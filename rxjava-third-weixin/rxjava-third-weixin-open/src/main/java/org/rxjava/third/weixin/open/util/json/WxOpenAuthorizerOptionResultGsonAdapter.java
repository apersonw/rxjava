package top.rxjava.third.weixin.open.util.json;

import com.google.gson.*;
import top.rxjava.third.weixin.common.util.json.GsonHelper;
import top.rxjava.third.weixin.open.bean.result.WxOpenAuthorizerOptionResult;

import java.lang.reflect.Type;

/**
 */
public class WxOpenAuthorizerOptionResultGsonAdapter implements JsonDeserializer<WxOpenAuthorizerOptionResult> {
    @Override
    public WxOpenAuthorizerOptionResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxOpenAuthorizerOptionResult authorizerOptionResult = new WxOpenAuthorizerOptionResult();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        authorizerOptionResult.setAuthorizerAppid(GsonHelper.getString(jsonObject, "authorizer_appid"));
        authorizerOptionResult.setOptionName(GsonHelper.getString(jsonObject, "option_name"));
        authorizerOptionResult.setOptionValue(GsonHelper.getString(jsonObject, "option_value"));
        return authorizerOptionResult;
    }
}
