package top.rxjava.third.weixin.mp.util.json;

import com.google.gson.*;
import top.rxjava.third.weixin.common.util.json.GsonHelper;
import top.rxjava.third.weixin.mp.bean.card.WxMpCard;

import java.lang.reflect.Type;

/**
 * Created by YuJian on 15/11/11.
 *
 * @version 15/11/11
 */
public class WxMpCardGsonAdapter implements JsonDeserializer<WxMpCard> {

    @Override
    public WxMpCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext
            jsonDeserializationContext) throws JsonParseException {
        WxMpCard card = new WxMpCard();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        card.setCardId(GsonHelper.getString(jsonObject, "card_id"));
        card.setBeginTime(GsonHelper.getLong(jsonObject, "begin_time"));
        card.setEndTime(GsonHelper.getLong(jsonObject, "end_time"));
        card.setUserCardStatus(GsonHelper.getString(jsonObject, "user_card_status"));
        card.setMembershipNumber(GsonHelper.getString(jsonObject, "membership_number"));
        card.setCode(GsonHelper.getString(jsonObject, "code"));
        card.setBonus(GsonHelper.getInteger(jsonObject, "bonus"));

        return card;
    }

}
