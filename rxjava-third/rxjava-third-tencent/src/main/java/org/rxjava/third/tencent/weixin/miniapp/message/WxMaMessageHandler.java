package org.rxjava.third.tencent.weixin.miniapp.message;

import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.session.WxSessionManager;
import org.rxjava.third.tencent.weixin.miniapp.api.WxMaService;
import org.rxjava.third.tencent.weixin.miniapp.bean.WxMaMessage;

import java.util.Map;

/**
 * 处理小程序推送消息的处理器接口.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxMaMessageHandler {
    /**
     * 处理消息.
     *
     * @param message        输入消息
     * @param context        上下文
     * @param service        服务类
     * @param sessionManager session管理器
     * @return 输出消息
     * @throws WxErrorException 异常
     */
    WxMaXmlOutMessage handle(WxMaMessage message, Map<String, Object> context,
                             WxMaService service, WxSessionManager sessionManager) throws WxErrorException;

}
