package top.rxjava.third.weixin.cp.api.impl;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.HttpType;
import top.rxjava.third.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import top.rxjava.third.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import top.rxjava.third.weixin.cp.config.WxCpTpConfigStorage;
import top.rxjava.third.weixin.cp.constant.WxCpApiPathConsts;

import java.io.IOException;

/**
 */
public class WxCpTpServiceApacheHttpClientImpl extends BaseWxCpTpServiceImpl<CloseableHttpClient, HttpHost> {
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
    public String getSuiteAccessToken(boolean forceRefresh) throws WxErrorException {
        if (!this.configStorage.isSuiteAccessTokenExpired() && !forceRefresh) {
            return this.configStorage.getSuiteAccessToken();
        }

        synchronized (this.globalSuiteAccessTokenRefreshLock) {
            try {
                HttpPost httpPost = new HttpPost(configStorage.getApiUrl(WxCpApiPathConsts.Tp.GET_SUITE_TOKEN));
                if (this.httpProxy != null) {
                    RequestConfig config = RequestConfig.custom()
                            .setProxy(this.httpProxy).build();
                    httpPost.setConfig(config);
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("suite_id", this.configStorage.getSuiteId());
                jsonObject.addProperty("suite_secret", this.configStorage.getSuiteSecret());
                jsonObject.addProperty("suite_ticket", this.getSuiteTicket());
                StringEntity entity = new StringEntity(jsonObject.toString(), Consts.UTF_8);
                httpPost.setEntity(entity);

                String resultContent;
                try (CloseableHttpClient httpclient = getRequestHttpClient();
                     CloseableHttpResponse response = httpclient.execute(httpPost)) {
                    resultContent = new BasicResponseHandler().handleResponse(response);
                } finally {
                    httpPost.releaseConnection();
                }
                WxError error = WxError.fromJson(resultContent, WxType.CP);
                if (error.getErrorCode() != 0) {
                    throw new WxErrorException(error);
                }
                jsonObject = new JsonParser().parse(resultContent).getAsJsonObject();
                String suiteAccussToken = jsonObject.get("suite_access_token").getAsString();
                Integer expiresIn = jsonObject.get("expires_in").getAsInt();
                this.configStorage.updateSuiteAccessToken(suiteAccussToken, expiresIn);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.configStorage.getSuiteAccessToken();
    }

    @Override
    public void initHttp() {
        ApacheHttpClientBuilder apacheHttpClientBuilder = this.configStorage.getApacheHttpClientBuilder();
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
    public WxCpTpConfigStorage getWxCpTpConfigStorage() {
        return this.configStorage;
    }

}
