package org.rxjava.third.tencent.weixin.mp.bean;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.bean.material.WxMpNewsArticle;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 群发时用到的图文消息素材.
 */
@Data
public class WxMpMassNews implements Serializable {
    private static final long serialVersionUID = 565937155013581016L;

    private List<WxMpNewsArticle> articles = new ArrayList<>();

    public void addArticle(WxMpNewsArticle article) {
        this.articles.add(article);
    }

    public String toJson() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public boolean isEmpty() {
        return this.articles == null || this.articles.isEmpty();
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
