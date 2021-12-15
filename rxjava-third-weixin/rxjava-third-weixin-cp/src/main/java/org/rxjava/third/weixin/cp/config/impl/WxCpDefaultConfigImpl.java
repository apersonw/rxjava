package top.rxjava.third.weixin.cp.config.impl;

import top.rxjava.third.weixin.common.bean.WxAccessToken;
import top.rxjava.third.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import top.rxjava.third.weixin.cp.config.WxCpConfigStorage;
import top.rxjava.third.weixin.cp.constant.WxCpApiPathConsts;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化.
 */
public class WxCpDefaultConfigImpl implements WxCpConfigStorage, Serializable {
    private static final long serialVersionUID = 1154541446729462780L;

    private volatile String corpId;
    private volatile String corpSecret;

    private volatile String token;
    protected volatile String accessToken;
    protected Lock accessTokenLock = new ReentrantLock();
    private volatile String aesKey;
    protected volatile Integer agentId;
    private volatile long expiresTime;

    private volatile String oauth2redirectUri;

    private volatile String httpProxyHost;
    private volatile int httpProxyPort;
    private volatile String httpProxyUsername;
    private volatile String httpProxyPassword;

    private volatile String jsapiTicket;
    protected Lock jsapiTicketLock = new ReentrantLock();
    private volatile long jsapiTicketExpiresTime;

    private volatile String agentJsapiTicket;
    protected Lock agentJsapiTicketLock = new ReentrantLock();
    private volatile long agentJsapiTicketExpiresTime;

    private volatile File tmpDirFile;

    private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

    private volatile String baseApiUrl;

    @Override
    public void setBaseApiUrl(String baseUrl) {
        this.baseApiUrl = baseUrl;
    }

    @Override
    public String getApiUrl(String path) {
        if (baseApiUrl == null) {
            baseApiUrl = WxCpApiPathConsts.DEFAULT_CP_BASE_URL;
        }
        return baseApiUrl + path;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.expiresTime;
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public synchronized void updateAccessToken(WxAccessToken accessToken) {
        updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getJsapiTicket() {
        return this.jsapiTicket;
    }

    @Override
    public Lock getJsapiTicketLock() {
        return this.jsapiTicketLock;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public long getJsapiTicketExpiresTime() {
        return this.jsapiTicketExpiresTime;
    }

    public void setJsapiTicketExpiresTime(long jsapiTicketExpiresTime) {
        this.jsapiTicketExpiresTime = jsapiTicketExpiresTime;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
    }

    @Override
    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        this.jsapiTicket = jsapiTicket;
        // 预留200秒的时间
        this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getAgentJsapiTicket() {
        return this.agentJsapiTicket;
    }

    @Override
    public Lock getAgentJsapiTicketLock() {
        return this.agentJsapiTicketLock;
    }

    @Override
    public boolean isAgentJsapiTicketExpired() {
        return System.currentTimeMillis() > this.agentJsapiTicketExpiresTime;
    }

    @Override
    public void expireAgentJsapiTicket() {
        this.agentJsapiTicketExpiresTime = 0;
    }

    @Override
    public void updateAgentJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        this.agentJsapiTicket = jsapiTicket;
        // 预留200秒的时间
        this.agentJsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public void expireJsapiTicket() {
        this.jsapiTicketExpiresTime = 0;
    }

    @Override
    public String getCorpId() {
        return this.corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    @Override
    public String getCorpSecret() {
        return this.corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public long getExpiresTime() {
        return this.expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Override
    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public Integer getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    public String getOauth2redirectUri() {
        return this.oauth2redirectUri;
    }

    public void setOauth2redirectUri(String oauth2redirectUri) {
        this.oauth2redirectUri = oauth2redirectUri;
    }

    @Override
    public String getHttpProxyHost() {
        return this.httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    @Override
    public int getHttpProxyPort() {
        return this.httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    @Override
    public String getHttpProxyUsername() {
        return this.httpProxyUsername;
    }

    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    @Override
    public String getHttpProxyPassword() {
        return this.httpProxyPassword;
    }

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override
    public String toString() {
        return WxCpGsonBuilder.create().toJson(this);
    }

    @Override
    public File getTmpDirFile() {
        return this.tmpDirFile;
    }

    public void setTmpDirFile(File tmpDirFile) {
        this.tmpDirFile = tmpDirFile;
    }

    @Override
    public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
        return this.apacheHttpClientBuilder;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
        this.apacheHttpClientBuilder = apacheHttpClientBuilder;
    }
}
