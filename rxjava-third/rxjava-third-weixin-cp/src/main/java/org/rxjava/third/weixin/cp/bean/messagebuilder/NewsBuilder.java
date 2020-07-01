package org.rxjava.third.weixin.cp.bean.messagebuilder;

import org.rxjava.third.weixin.common.api.WxConsts;
import org.rxjava.third.weixin.cp.bean.WxCpMessage;
import org.rxjava.third.weixin.cp.bean.article.NewArticle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 图文消息builder
 * <p>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {

    private List<NewArticle> articles = new ArrayList<>();

    public NewsBuilder() {
        this.msgType = WxConsts.KefuMsgType.NEWS;
    }

    public NewsBuilder addArticle(NewArticle... articles) {
        Collections.addAll(this.articles, articles);
        return this;
    }

    public NewsBuilder articles(List<NewArticle> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    public WxCpMessage build() {
        WxCpMessage m = super.build();
        m.setArticles(this.articles);
        return m;
    }
}
