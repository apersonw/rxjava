package org.rxjava.third.tencent.weixin.mp.util.json;

import com.google.gson.*;
import org.rxjava.third.tencent.weixin.common.util.json.GsonHelper;
import org.rxjava.third.tencent.weixin.mp.bean.membercard.WxMpMemberCardUpdateResult;

import java.lang.reflect.Type;

/**
 * Json to WxMpMemberCardUpdateResult 的转换适配器
 *
 * @version 2017/7/15
 */
public class WxMpMemberCardUpdateResultGsonAdapter implements JsonDeserializer<WxMpMemberCardUpdateResult> {

    @Override
    public WxMpMemberCardUpdateResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext
            jsonDeserializationContext) throws JsonParseException {

        WxMpMemberCardUpdateResult result = new WxMpMemberCardUpdateResult();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        result.setOpenId(GsonHelper.getString(jsonObject, "openid"));
        result.setErrorCode(GsonHelper.getString(jsonObject, "errcode"));
        result.setErrorMsg(GsonHelper.getString(jsonObject, "errmsg"));
        result.setResultBalance(GsonHelper.getDouble(jsonObject, "result_balance"));
        result.setResultBonus(GsonHelper.getInteger(jsonObject, "result_bonus"));

        return result;
    }
}
