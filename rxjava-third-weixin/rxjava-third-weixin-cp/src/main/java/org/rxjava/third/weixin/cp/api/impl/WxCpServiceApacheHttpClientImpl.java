package org.rxjava.third.weixin.cp.api.impl;


import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.bean.WxAccessToken;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.http.HttpType;
import org.rxjava.third.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import org.rxjava.third.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import org.rxjava.third.weixin.cp.config.WxCpConfigStorage;
import org.rxjava.third.weixin.cp.constant.WxCpApiPathConsts;

import java.io.IOException;

/**
 */
public class WxCpServiceApacheHttpClientImpl extends BaseWxCpServiceImpl<CloseableHttpClient, HttpHost> {
    private CloseableHttpClient httpClient;
    private HttpHost httpProxy;

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public HttpHost getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.APACHE_HTTP;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        if (!this.configStorage.isAccessTokenExpired() && !forceRefresh) {
            return this.configStorage.getAccessToken();
        }

        synchronized (this.globalAccessTokenRefreshLock) {
            String url = String.format(this.configStorage.getApiUrl(WxCpApiPathConsts.GET_TOKEN), this.configStorage.getCorpId(), this.configStorage.getCorpSecret());

            try {
                HttpGet httpGet = new HttpGet(url);
                if (this.httpProxy != null) {
                    RequestConfig config = RequestConfig.custom()
                            .setProxy(this.httpProxy).build();
                    httpGet.setConfig(config);
                }
                String resultContent;
                try (CloseableHttpClient httpClient = getRequestHttpClient();
                     CloseableHttpResponse response = httpClient.execute(httpGet)) {
                    resultContent = new BasicResponseHandler().handleResponse(response);
                } finally {
                    httpGet.releaseConnection();
                }
                WxError error = WxError.fromJson(resultContent, WxType.CP);
                if (error.getErrorCode() != 0) {
                    throw new WxErrorException(error);
                }

                WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
                this.configStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.configStorage.getAccessToken();
    }

    @Override
    public void initHttp() {
        ApacheHttpClientBuilder apacheHttpClientBuilder = this.configStorage
                .getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }

        apacheHttpClientBuilder.httpProxyHost(this.configStorage.getHttpProxyHost())
                .httpProxyPort(this.configStorage.getHttpProxyPort())
                .httpProxyUsername(this.configStorage.getHttpProxyUsername())
                .httpProxyPassword(this.configStorage.getHttpProxyPassword());

        if (this.configStorage.getHttpProxyHost() != null && this.configStorage.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(this.configStorage.getHttpProxyHost(), this.configStorage.getHttpProxyPort());
        }

        this.httpClient = apacheHttpClientBuilder.build();
    }

    @Override
    public WxCpConfigStorage getWxCpConfigStorage() {
        return this.configStorage;
    }

}
