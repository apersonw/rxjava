package org.rxjava.third.weixin.mp.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.rxjava.third.weixin.mp.bean.material.WxMpMaterialArticleUpdate;
import org.rxjava.third.weixin.mp.bean.material.WxMpNewsArticle;

import java.lang.reflect.Type;

public class WxMpMaterialArticleUpdateGsonAdapter implements JsonSerializer<WxMpMaterialArticleUpdate> {

    @Override
    public JsonElement serialize(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject articleUpdateJson = new JsonObject();
        articleUpdateJson.addProperty("media_id", wxMpMaterialArticleUpdate.getMediaId());
        articleUpdateJson.addProperty("index", wxMpMaterialArticleUpdate.getIndex());
        articleUpdateJson.add("articles", WxMpGsonBuilder.create().toJsonTree(wxMpMaterialArticleUpdate.getArticles(), WxMpNewsArticle.class));
        return articleUpdateJson;
    }

}
