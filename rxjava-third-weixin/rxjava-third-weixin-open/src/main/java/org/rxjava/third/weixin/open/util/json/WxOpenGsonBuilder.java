package top.rxjava.third.weixin.open.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.rxjava.third.weixin.open.bean.WxOpenAuthorizerAccessToken;
import top.rxjava.third.weixin.open.bean.WxOpenComponentAccessToken;
import top.rxjava.third.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import top.rxjava.third.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import top.rxjava.third.weixin.open.bean.result.*;

/**
 */
public class WxOpenGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxOpenComponentAccessToken.class, new WxOpenComponentAccessTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerAccessToken.class, new WxOpenAuthorizerAccessTokenGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizationInfo.class, new WxOpenAuthorizationInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerInfo.class, new WxOpenAuthorizerInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenQueryAuthResult.class, new WxOpenQueryAuthResultGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerInfoResult.class, new WxOpenAuthorizerInfoResultGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerOptionResult.class, new WxOpenAuthorizerOptionResultGsonAdapter());
        INSTANCE.registerTypeAdapter(WxFastMaAccountBasicInfoResult.class, new WxFastMaAccountBasicInfoGsonAdapter());
        INSTANCE.registerTypeAdapter(WxOpenAuthorizerListResult.class, new WxOpenAuthorizerListResultGsonAdapter());

    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
