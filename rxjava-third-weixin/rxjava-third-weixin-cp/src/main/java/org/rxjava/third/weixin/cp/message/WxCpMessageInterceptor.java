package top.rxjava.third.weixin.cp.message;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.session.WxSessionManager;
import top.rxjava.third.weixin.cp.api.WxCpService;
import top.rxjava.third.weixin.cp.bean.WxCpXmlMessage;

import java.util.Map;

/**
 * 微信消息拦截器，可以用来做验证
 */
public interface WxCpMessageInterceptor {

    /**
     * 拦截微信消息
     *
     * @param wxMessage
     * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param wxCpService
     * @param sessionManager
     * @return true代表OK，false代表不OK
     */
    boolean intercept(WxCpXmlMessage wxMessage,
                      Map<String, Object> context,
                      WxCpService wxCpService,
                      WxSessionManager sessionManager) throws WxErrorException;

}
