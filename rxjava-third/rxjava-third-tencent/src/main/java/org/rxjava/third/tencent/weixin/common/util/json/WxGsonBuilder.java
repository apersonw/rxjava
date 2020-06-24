package org.rxjava.third.tencent.weixin.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.rxjava.third.tencent.weixin.common.bean.WxAccessToken;
import org.rxjava.third.tencent.weixin.common.bean.WxNetCheckResult;
import org.rxjava.third.tencent.weixin.common.bean.menu.WxMenu;
import org.rxjava.third.tencent.weixin.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.tencent.weixin.common.error.WxError;

/**
 * .
 *
 * @author chanjarster
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
