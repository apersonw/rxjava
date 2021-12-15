package top.rxjava.third.weixin.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.rxjava.third.weixin.common.bean.WxAccessToken;
import top.rxjava.third.weixin.common.bean.WxNetCheckResult;
import top.rxjava.third.weixin.common.bean.menu.WxMenu;
import top.rxjava.third.weixin.common.bean.result.WxMediaUploadResult;
import top.rxjava.third.weixin.common.error.WxError;

/**
 */
public class WxGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxAccessTokenAdapter());
        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
        INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
        INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxMediaUploadResultAdapter());
        INSTANCE.registerTypeAdapter(WxNetCheckResult.class, new WxNetCheckResultGsonAdapter());

    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
