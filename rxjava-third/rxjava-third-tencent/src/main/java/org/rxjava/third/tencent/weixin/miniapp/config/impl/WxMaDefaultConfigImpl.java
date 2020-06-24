package org.rxjava.third.tencent.weixin.miniapp.config.impl;

import org.rxjava.third.tencent.weixin.common.bean.WxAccessToken;
import org.rxjava.third.tencent.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import org.rxjava.third.tencent.weixin.miniapp.config.WxMaConfig;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 */
public class WxMaDefaultConfigImpl implements WxMaConfig {
    protected volatile String appid;
    protected volatile String token;
    /**
     * 小程序原始ID
     */
    protected volatile String originalId;
    protected Lock accessTokenLock = new ReentrantLock();
    /**
     * 临时文件目录.
     */
    protected volatile File tmpDirFile;
    private volatile String msgDataFormat;
    private volatile String secret;
    private volatile String accessToken;
    private volatile String aesKey;
    private volatile long expiresTime;
    /**
     * 云环境ID
     */
    private volatile String cloudEnv;
    private volatile String httpProxyHost;
    private volatile int httpProxyPort;
    private volatile String httpProxyUsername;
    private volatile String httpProxyPassword;
    private volatile String jsapiTicket;
    private volatile long jsapiTicketExpiresTime;
    /**
     * 微信卡券的ticket单独缓存.
     */
    private volatile String cardApiTicket;
    private volatile long cardApiTicketExpiresTime;
    protected volatile Lock jsapiTicketLock = new ReentrantLock();
    protected volatile Lock cardApiTicketLock = new ReentrantLock();
    private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

    /**
     * 会过期的数据提前过期时间，默认预留200秒的时间
     */
    protected long expiresAheadInMillis(int expiresInSeconds) {
        return System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    /**
     * 判断 expiresTime 是否已经过期
     */
    protected boolean isExpired(long expiresTime) {
        return System.currentTimeMillis() > expiresTime;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    public void setAccessTokenLock(Lock accessTokenLock) {
        this.accessTokenLock = accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return isExpired(this.expiresTime);
    }

    @Override
    public synchronized void updateAccessToken(WxAccessToken accessToken) {
        updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        setAccessToken(accessToken);
        setExpiresTime(expiresAheadInMillis(expiresInSeconds));
    }

    @Override
    public String getJsapiTicket() {
        return this.jsapiTicket;
    }

    @Override
    public Lock getJsapiTicketLock() {
        return this.jsapiTicketLock;
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return isExpired(this.jsapiTicketExpiresTime);
    }

    @Override
    public void expireJsapiTicket() {
        this.jsapiTicketExpiresTime = 0;
    }

    @Override
    public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        this.jsapiTicket = jsapiTicket;
        this.jsapiTicketExpiresTime = expiresAheadInMillis(expiresInSeconds);
    }


    @Override
    public String getCardApiTicket() {
        return this.cardApiTicket;
    }

    @Override
    public Lock getCardApiTicketLock() {
        return this.cardApiTicketLock;
    }

    @Override
    public boolean isCardApiTicketExpired() {
        return isExpired(this.cardApiTicketExpiresTime);
    }

    @Override
    public void expireCardApiTicket() {
        this.cardApiTicketExpiresTime = 0;
    }

    @Override
    public void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
        this.cardApiTicket = cardApiTicket;
        this.cardApiTicketExpiresTime = expiresAheadInMillis(expiresInSeconds);
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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
    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    @Override
    public String getCloudEnv() {
        return this.cloudEnv;
    }

    public void setCloudEnv(String cloudEnv) {
        this.cloudEnv = cloudEnv;
    }

    @Override
    public String getMsgDataFormat() {
        return this.msgDataFormat;
    }

    public void setMsgDataFormat(String msgDataFormat) {
        this.msgDataFormat = msgDataFormat;
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
        return WxMaGsonBuilder.create().toJson(this);
    }

    @Override
    public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
        return this.apacheHttpClientBuilder;
    }

    public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
        this.apacheHttpClientBuilder = apacheHttpClientBuilder;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    @Override
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
