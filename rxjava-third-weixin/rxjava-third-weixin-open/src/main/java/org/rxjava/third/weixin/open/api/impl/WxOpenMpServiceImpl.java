package top.rxjava.third.weixin.open.api.impl;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.mp.api.impl.WxMpServiceImpl;
import top.rxjava.third.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import top.rxjava.third.weixin.mp.config.WxMpConfigStorage;
import top.rxjava.third.weixin.open.api.WxOpenComponentService;

/**
 */
public class WxOpenMpServiceImpl extends WxMpServiceImpl {
    private WxOpenComponentService wxOpenComponentService;
    private WxMpConfigStorage wxMpConfigStorage;
    private String appId;

    public WxOpenMpServiceImpl(WxOpenComponentService wxOpenComponentService, String appId, WxMpConfigStorage wxMpConfigStorage) {
        this.wxOpenComponentService = wxOpenComponentService;
        this.appId = appId;
        this.wxMpConfigStorage = wxMpConfigStorage;
        initHttp();
    }

    @Override
    public WxMpConfigStorage getWxMpConfigStorage() {
        return wxMpConfigStorage;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        return wxOpenComponentService.getAuthorizerAccessToken(appId, forceRefresh);
    }

    @Override
    public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
        return wxOpenComponentService.oauth2getAccessToken(appId, code);
    }

    @Override
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException {
        return wxOpenComponentService.oauth2refreshAccessToken(appId, refreshToken);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        return wxOpenComponentService.oauth2buildAuthorizationUrl(appId, redirectURI, scope, state);
    }
}
