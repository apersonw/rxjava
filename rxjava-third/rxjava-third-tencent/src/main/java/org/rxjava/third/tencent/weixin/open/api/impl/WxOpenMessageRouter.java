package org.rxjava.third.tencent.weixin.open.api.impl;

import org.rxjava.third.tencent.weixin.mp.api.WxMpMessageRouter;
import org.rxjava.third.tencent.weixin.mp.bean.message.WxMpXmlMessage;
import org.rxjava.third.tencent.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.rxjava.third.tencent.weixin.open.api.WxOpenService;

import java.util.HashMap;
import java.util.Map;

public class WxOpenMessageRouter extends WxMpMessageRouter {
    private WxOpenService wxOpenService;

    public WxOpenMessageRouter(WxOpenService wxOpenService) {
        super(null);
        this.wxOpenService = wxOpenService;
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, String appId) {
        return route(wxMessage, new HashMap<String, Object>(), appId);
    }

    public WxMpXmlOutMessage route(final WxMpXmlMessage wxMessage, final Map<String, Object> context, String appId) {
        return route(wxMessage, context, wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId));
    }
}
