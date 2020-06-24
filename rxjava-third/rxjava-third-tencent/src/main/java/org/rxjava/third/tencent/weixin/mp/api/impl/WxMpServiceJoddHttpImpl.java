package org.rxjava.third.tencent.weixin.mp.api.impl;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.HttpType;
import org.rxjava.third.tencent.weixin.mp.config.WxMpConfigStorage;

import java.util.concurrent.locks.Lock;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.Other.GET_ACCESS_TOKEN_URL;

/**
 * jodd-http方式实现.
 */
public class WxMpServiceJoddHttpImpl extends BaseWxMpServiceImpl<HttpConnectionProvider, ProxyInfo> {
    private HttpConnectionProvider httpClient;
    private ProxyInfo httpProxy;

    @Override
    public HttpConnectionProvider getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public ProxyInfo getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.JODD_HTTP;
    }

    @Override
    public void initHttp() {

        WxMpConfigStorage configStorage = this.getWxMpConfigStorage();

        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            httpProxy = new ProxyInfo(ProxyInfo.ProxyType.HTTP, configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort(), configStorage.getHttpProxyUsername(), configStorage.getHttpProxyPassword());
        }

        httpClient = new SocketHttpConnectionProvider();
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        final WxMpConfigStorage config = this.getWxMpConfigStorage();
        if (!config.isAccessTokenExpired() && !forceRefresh) {
            return config.getAccessToken();
        }

        Lock lock = config.getAccessTokenLock();
        lock.lock();
        try {
            if (!config.isAccessTokenExpired() && !forceRefresh) {
                return config.getAccessToken();
            }
            String url = String.format(GET_ACCESS_TOKEN_URL.getUrl(config), config.getAppId(), config.getSecret());

            HttpRequest request = HttpRequest.get(url);
            if (this.getRequestHttpProxy() != null) {
                SocketHttpConnectionProvider provider = new SocketHttpConnectionProvider();
                provider.useProxy(getRequestHttpProxy());

                request.withConnectionProvider(provider);
            }

            return this.extractAccessToken(request.send().bodyText());
        } finally {
            lock.unlock();
        }
    }

}
