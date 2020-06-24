package org.rxjava.third.tencent.miniapp.util.json;

import org.rxjava.third.tencent.miniapp.bean.WxMaSubscribeMessage;
import org.rxjava.third.tencent.miniapp.bean.WxMaTemplateMessage;
import org.rxjava.third.tencent.miniapp.bean.WxMaUniformMessage;
import org.rxjava.third.tencent.miniapp.bean.analysis.WxMaRetainInfo;
import org.rxjava.third.tencent.miniapp.bean.analysis.WxMaUserPortrait;
import org.rxjava.third.tencent.miniapp.bean.analysis.WxMaVisitDistribution;
import org.rxjava.third.tencent.miniapp.bean.code.WxMaCodeCommitRequest;
import org.rxjava.third.tencent.miniapp.bean.code.WxMaCodeVersionDistribution;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
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
