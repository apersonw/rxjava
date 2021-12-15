package top.rxjava.third.weixin.mp.util.json;

import com.google.gson.*;
import top.rxjava.third.weixin.common.util.json.GsonHelper;
import top.rxjava.third.weixin.mp.bean.material.WxMpMaterialNews;
import top.rxjava.third.weixin.mp.bean.material.WxMpNewsArticle;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WxMpMaterialNewsGsonAdapter implements JsonSerializer<WxMpMaterialNews>, JsonDeserializer<WxMpMaterialNews> {

    @Override
    public JsonElement serialize(WxMpMaterialNews wxMpMaterialNews, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject newsJson = new JsonObject();

        JsonArray articleJsonArray = new JsonArray();
        for (WxMpNewsArticle article : wxMpMaterialNews.getArticles()) {
            JsonObject articleJson = WxMpGsonBuilder.create().toJsonTree(article, WxMpNewsArticle.class).getAsJsonObject();
            articleJsonArray.add(articleJson);
        }
        newsJson.add("articles", articleJsonArray);

        if (wxMpMaterialNews.getCreateTime() != null) {
            newsJson.addProperty("create_time",
                    SimpleDateFormat.getDateTimeInstance().format(wxMpMaterialNews.getCreateTime()));
        }

        if (wxMpMaterialNews.getUpdateTime() != null) {
            newsJson.addProperty("update_time",
                    SimpleDateFormat.getDateTimeInstance().format(wxMpMaterialNews.getUpdateTime()));
        }

        return newsJson;
    }

    @Override
    public WxMpMaterialNews deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        WxMpMaterialNews wxMpMaterialNews = new WxMpMaterialNews();
        JsonObject json = jsonElement.getAsJsonObject();
        if (json.get("news_item") != null && !json.get("news_item").isJsonNull()) {
            JsonArray articles = json.getAsJsonArray("news_item");
            for (JsonElement article1 : articles) {
                JsonObject articleInfo = article1.getAsJsonObject();
                WxMpNewsArticle article = WxMpGsonBuilder.create().fromJson(articleInfo, WxMpNewsArticle.class);
                wxMpMaterialNews.addArticle(article);
            }
        }

        if (json.get("create_time") != null && !json.get("create_time").isJsonNull()) {
            Date createTime = new Date(GsonHelper.getAsLong(json.get("create_time")) * 1000);
            wxMpMaterialNews.setCreateTime(createTime);
        }

        if (json.get("update_time") != null && !json.get("update_time").isJsonNull()) {
            Date updateTime = new Date(GsonHelper.getAsLong(json.get("update_time")) * 1000);
            wxMpMaterialNews.setUpdateTime(updateTime);
        }

        return wxMpMaterialNews;
    }
}
