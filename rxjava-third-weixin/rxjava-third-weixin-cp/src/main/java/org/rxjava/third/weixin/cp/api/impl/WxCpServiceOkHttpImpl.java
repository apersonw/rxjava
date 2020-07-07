package org.rxjava.third.weixin.cp.api.impl;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.bean.WxAccessToken;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.http.HttpType;
import org.rxjava.third.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import org.rxjava.third.weixin.cp.config.WxCpConfigStorage;

import java.io.IOException;

import static org.rxjava.third.weixin.cp.constant.WxCpApiPathConsts.GET_TOKEN;

/**
 */
@Slf4j
public class WxCpServiceOkHttpImpl extends BaseWxCpServiceImpl<OkHttpClient, OkHttpProxyInfo> {
    private OkHttpClient httpClient;
    private OkHttpProxyInfo httpProxy;

    @Override
    public OkHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public OkHttpProxyInfo getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.OK_HTTP;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        if (!this.configStorage.isAccessTokenExpired() && !forceRefresh) {
            return this.configStorage.getAccessToken();
        }

        synchronized (this.globalAccessTokenRefreshLock) {
            //得到httpClient
            OkHttpClient client = getRequestHttpClient();
            //请求的request
            Request request = new Request.Builder()
                    .url(String.format(this.configStorage.getApiUrl(GET_TOKEN), this.configStorage.getCorpId(), this.configStorage.getCorpSecret()))
                    .get()
                    .build();
            String resultContent = null;
            try {
                Response response = client.newCall(request).execute();
                resultContent = response.body().string();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

            WxError error = WxError.fromJson(resultContent, WxType.CP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            this.configStorage.updateAccessToken(accessToken.getAccessToken(),
                    accessToken.getExpiresIn());
        }
        return this.configStorage.getAccessToken();
    }

    @Override
    public void initHttp() {
        log.debug("WxCpServiceOkHttpImpl initHttp");
        //设置代理
        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            httpProxy = OkHttpProxyInfo.httpProxy(configStorage.getHttpProxyHost(),
                    configStorage.getHttpProxyPort(),
                    configStorage.getHttpProxyUsername(),
                    configStorage.getHttpProxyPassword());
        }

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (httpProxy != null) {
            clientBuilder.proxy(getRequestHttpProxy().getProxy());

            //设置授权
            clientBuilder.authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic(httpProxy.getProxyUsername(), httpProxy.getProxyPassword());
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                }
            });
        }
        httpClient = clientBuilder.build();
    }

    @Override
    public WxCpConfigStorage getWxCpConfigStorage() {
        return this.configStorage;
    }
}
