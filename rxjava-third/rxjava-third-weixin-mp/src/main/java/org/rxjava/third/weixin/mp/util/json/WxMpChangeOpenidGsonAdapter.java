package org.rxjava.third.weixin.mp.util.json;

import com.google.gson.*;
import org.rxjava.third.weixin.common.util.json.GsonHelper;
import org.rxjava.third.weixin.mp.bean.result.WxMpChangeOpenid;

import java.lang.reflect.Type;

public class WxMpChangeOpenidGsonAdapter implements JsonDeserializer<WxMpChangeOpenid> {

    @Override
    public WxMpChangeOpenid deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject o = json.getAsJsonObject();
        WxMpChangeOpenid changeOpenid = new WxMpChangeOpenid();
        changeOpenid.setOriOpenid(GsonHelper.getString(o, "ori_openid"));
        changeOpenid.setNewOpenid(GsonHelper.getString(o, "new_openid"));
        changeOpenid.setErrMsg(GsonHelper.getString(o, "err_msg"));
        return changeOpenid;
    }

}
