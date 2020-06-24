package org.rxjava.third.tencent.weixin.mp.api.impl;

import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.URIUtil;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpSubscribeMsgService;
import org.rxjava.third.tencent.weixin.mp.bean.subscribe.WxMpSubscribeMessage;
import org.rxjava.third.tencent.weixin.mp.config.WxMpConfigStorage;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.SubscribeMsg.SEND_MESSAGE_URL;
import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.SubscribeMsg.SUBSCRIBE_MESSAGE_AUTHORIZE_URL;

/**
 * 一次性订阅消息接口.
 *
 * @author Mklaus
 * @date 2018-01-22 上午11:19
 */
@RequiredArgsConstructor
public class WxMpSubscribeMsgServiceImpl implements WxMpSubscribeMsgService {
    private final WxMpService wxMpService;

    @Override
    public String subscribeMsgAuthorizationUrl(String redirectURI, int scene, String reserved) {
        WxMpConfigStorage storage = this.wxMpService.getWxMpConfigStorage();
        return String.format(SUBSCRIBE_MESSAGE_AUTHORIZE_URL.getUrl(storage), storage.getAppId(), scene, storage.getTemplateId(),
                URIUtil.encodeURIComponent(redirectURI), reserved);
    }

    @Override
    public boolean sendSubscribeMessage(WxMpSubscribeMessage message) throws WxErrorException {
        if (message.getTemplateId() == null) {
            message.setTemplateId(this.wxMpService.getWxMpConfigStorage().getTemplateId());
        }

        String responseContent = this.wxMpService.post(SEND_MESSAGE_URL, message.toJson());
        return responseContent != null;
    }
}
