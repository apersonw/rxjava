package org.rxjava.third.weixin.cp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.bean.WxAccessToken;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.cp.constant.WxCpApiPathConsts;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import static org.rxjava.third.weixin.cp.constant.WxCpApiPathConsts.GET_AGENT_CONFIG_TICKET;
import static org.rxjava.third.weixin.cp.constant.WxCpApiPathConsts.GET_JSAPI_TICKET;

/**
 * 默认接口实现类，使用apache httpclient实现
 * 增加分布式锁（基于WxCpConfigStorage实现）的支持
 */
public class WxCpServiceImpl extends WxCpServiceApacheHttpClientImpl {
    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        if (!getWxCpConfigStorage().isAccessTokenExpired() && !forceRefresh) {
            return getWxCpConfigStorage().getAccessToken();
        }
        Lock lock = getWxCpConfigStorage().getAccessTokenLock();
        lock.lock();
        try {
            // 拿到锁之后，再次判断一下最新的token是否过期，避免重刷
            if (!getWxCpConfigStorage().isAccessTokenExpired() && !forceRefresh) {
                return getWxCpConfigStorage().getAccessToken();
            }
            String url = String.format(getWxCpConfigStorage().getApiUrl(WxCpApiPathConsts.GET_TOKEN), this.configStorage.getCorpId(), this.configStorage.getCorpSecret());
            try {
                HttpGet httpGet = new HttpGet(url);
                if (getRequestHttpProxy() != null) {
                    RequestConfig config = RequestConfig.custom()
                            .setProxy(getRequestHttpProxy()).build();
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
                getWxCpConfigStorage().updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            lock.unlock();
        }
        return getWxCpConfigStorage().getAccessToken();
    }

    @Override
    public String getAgentJsapiTicket(boolean forceRefresh) throws WxErrorException {
        if (forceRefresh) {
            getWxCpConfigStorage().expireAgentJsapiTicket();
        }
        if (getWxCpConfigStorage().isAgentJsapiTicketExpired()) {
            Lock lock = getWxCpConfigStorage().getAgentJsapiTicketLock();
            lock.lock();
            try {
                // 拿到锁之后，再次判断一下最新的token是否过期，避免重刷
                if (getWxCpConfigStorage().isAgentJsapiTicketExpired()) {
                    String responseContent = this.get(getWxCpConfigStorage().getApiUrl(GET_AGENT_CONFIG_TICKET), null);
                    JsonObject jsonObject = new JsonParser().parse(responseContent).getAsJsonObject();
                    getWxCpConfigStorage().updateAgentJsapiTicket(jsonObject.get("ticket").getAsString(),
                            jsonObject.get("expires_in").getAsInt());
                }
            } finally {
                lock.unlock();
            }
        }
        return getWxCpConfigStorage().getAgentJsapiTicket();
    }

    @Override
    public String getJsapiTicket(boolean forceRefresh) throws WxErrorException {
        if (forceRefresh) {
            getWxCpConfigStorage().expireJsapiTicket();
        }

        if (getWxCpConfigStorage().isJsapiTicketExpired()) {
            Lock lock = getWxCpConfigStorage().getJsapiTicketLock();
            lock.lock();
            try {
                // 拿到锁之后，再次判断一下最新的token是否过期，避免重刷
                if (getWxCpConfigStorage().isJsapiTicketExpired()) {
                    String responseContent = this.get(getWxCpConfigStorage().getApiUrl(GET_JSAPI_TICKET), null);
                    JsonObject tmpJsonObject = new JsonParser().parse(responseContent).getAsJsonObject();
                    getWxCpConfigStorage().updateJsapiTicket(tmpJsonObject.get("ticket").getAsString(),
                            tmpJsonObject.get("expires_in").getAsInt());
                }
            } finally {
                lock.unlock();
            }
        }
        return getWxCpConfigStorage().getJsapiTicket();
    }
}
