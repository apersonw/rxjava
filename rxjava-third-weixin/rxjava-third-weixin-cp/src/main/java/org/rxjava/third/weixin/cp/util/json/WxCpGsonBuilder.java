package top.rxjava.third.weixin.cp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.rxjava.third.weixin.common.bean.menu.WxMenu;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.util.json.WxErrorAdapter;
import top.rxjava.third.weixin.cp.bean.WxCpChat;
import top.rxjava.third.weixin.cp.bean.WxCpDepart;
import top.rxjava.third.weixin.cp.bean.WxCpTag;
import top.rxjava.third.weixin.cp.bean.WxCpUser;

/**
 *
 */
public class WxCpGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxCpChat.class, new WxCpChatGsonAdapter());
        INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
        INSTANCE.registerTypeAdapter(WxCpUser.class, new WxCpUserGsonAdapter());
        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
        INSTANCE.registerTypeAdapter(WxMenu.class, new WxCpMenuGsonAdapter());
        INSTANCE.registerTypeAdapter(WxCpTag.class, new WxCpTagGsonAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
