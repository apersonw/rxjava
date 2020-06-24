package org.rxjava.third.tencent.weixin.open.api.impl;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.HttpType;
import org.rxjava.third.tencent.weixin.common.util.http.SimpleGetRequestExecutor;
import org.rxjava.third.tencent.weixin.common.util.http.SimplePostRequestExecutor;
import org.rxjava.third.tencent.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import org.rxjava.third.tencent.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import org.rxjava.third.tencent.weixin.open.api.WxOpenConfigStorage;

/**
 * apache-http方式实现
 */
public class WxOpenServiceApacheHttpClientImpl extends WxOpenServiceAbstractImpl<CloseableHttpClient, HttpHost> {
    private CloseableHttpClient httpClient;
    private HttpHost httpProxy;

    @Override
    public void initHttp() {
        WxOpenConfigStorage configStorage = this.getWxOpenConfigStorage();
        ApacheHttpClientBuilder apacheHttpClientBuilder = configStorage.getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }

        apacheHttpClientBuilder.httpProxyHost(configStorage.getHttpProxyHost())
                .httpProxyPort(configStorage.getHttpProxyPort())
                .httpProxyUsername(configStorage.getHttpProxyUsername())
                .httpProxyPassword(configStorage.getHttpProxyPassword());

        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort());
        }

        this.httpClient = apacheHttpClientBuilder.build();

    }

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
    public String get(String url, String queryParam) throws WxErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws WxErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }
}
