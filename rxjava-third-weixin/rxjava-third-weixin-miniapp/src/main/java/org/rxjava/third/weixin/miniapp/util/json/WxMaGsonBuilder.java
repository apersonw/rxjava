package top.rxjava.third.weixin.miniapp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.rxjava.third.weixin.miniapp.bean.WxMaSubscribeMessage;
import top.rxjava.third.weixin.miniapp.bean.WxMaTemplateMessage;
import top.rxjava.third.weixin.miniapp.bean.WxMaUniformMessage;
import top.rxjava.third.weixin.miniapp.bean.analysis.WxMaRetainInfo;
import top.rxjava.third.weixin.miniapp.bean.analysis.WxMaUserPortrait;
import top.rxjava.third.weixin.miniapp.bean.analysis.WxMaVisitDistribution;
import top.rxjava.third.weixin.miniapp.bean.code.WxMaCodeCommitRequest;
import top.rxjava.third.weixin.miniapp.bean.code.WxMaCodeVersionDistribution;

/**
 */
public class WxMaGsonBuilder {
    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxMaTemplateMessage.class, new WxMaTemplateMessageGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaSubscribeMessage.class, new WxMaSubscribeMessageGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaUniformMessage.class, new WxMaUniformMessageGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaCodeCommitRequest.class, new WxMaCodeCommitRequestGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaCodeVersionDistribution.class, new WxMaCodeVersionDistributionGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaVisitDistribution.class, new WxMaVisitDistributionGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaRetainInfo.class, new WxMaRetainInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMaUserPortrait.class, new WxMaUserPortraitGsonAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
