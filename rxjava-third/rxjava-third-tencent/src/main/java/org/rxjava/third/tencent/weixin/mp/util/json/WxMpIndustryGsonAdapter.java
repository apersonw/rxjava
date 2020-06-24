package org.rxjava.third.tencent.weixin.mp.util.json;

import com.google.gson.*;
import org.rxjava.third.tencent.weixin.common.util.json.GsonHelper;
import org.rxjava.third.tencent.weixin.mp.bean.template.WxMpTemplateIndustry;
import org.rxjava.third.tencent.weixin.mp.bean.template.WxMpTemplateIndustryEnum;

import java.lang.reflect.Type;

/**
 */
public class WxMpIndustryGsonAdapter implements JsonSerializer<WxMpTemplateIndustry>, JsonDeserializer<WxMpTemplateIndustry> {
    @Override
    public JsonElement serialize(WxMpTemplateIndustry wxMpIndustry, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("industry_id1", wxMpIndustry.getPrimaryIndustry().getCode());
        json.addProperty("industry_id2", wxMpIndustry.getSecondIndustry().getCode());
        return json;
    }

    @Override
    public WxMpTemplateIndustry deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        return new WxMpTemplateIndustry()
                .setPrimaryIndustry(this.convertFromJson(jsonElement.getAsJsonObject().get("primary_industry").getAsJsonObject()))
                .setSecondIndustry(this.convertFromJson(jsonElement.getAsJsonObject().get("secondary_industry").getAsJsonObject()));
    }

    private WxMpTemplateIndustryEnum convertFromJson(JsonObject json) {
        String secondClass = GsonHelper.getString(json, "second_class");
        final WxMpTemplateIndustryEnum industryEnum = WxMpTemplateIndustryEnum.findBySecondary(secondClass);
        if (industryEnum != null) {
            return industryEnum;
        }

        if (secondClass.contains("|")) {
            secondClass = secondClass.split("\\|")[1];
        }

        return WxMpTemplateIndustryEnum.findBySecondary(secondClass);
    }

}
