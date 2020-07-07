package org.rxjava.third.weixin.miniapp.message;

import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.session.WxSessionManager;
import org.rxjava.third.weixin.miniapp.api.WxMaService;
import org.rxjava.third.weixin.miniapp.bean.WxMaMessage;

import java.util.Map;

/**
 * 微信消息拦截器，可以用来做验证.
 */
public interface WxMaMessageInterceptor {

    /**
     * 拦截微信消息.
     *
     * @param wxMessage      .
     * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param wxMaService    .
     * @param sessionManager .
     * @return true代表OK，false代表不OK
     * @throws WxErrorException .
     */
    boolean intercept(WxMaMessage wxMessage,
                      Map<String, Object> context,
                      WxMaService wxMaService,
                      WxSessionManager sessionManager) throws WxErrorException;

}
