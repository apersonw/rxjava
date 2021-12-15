package top.rxjava.third.weixin.cp.config.impl;

import top.rxjava.third.weixin.common.bean.WxAccessToken;
import top.rxjava.third.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import top.rxjava.third.weixin.cp.config.WxCpConfigStorage;
import top.rxjava.third.weixin.cp.constant.WxCpApiPathConsts;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用说明：本实现仅供参考，并不完整.
 * 比如为减少项目依赖，未加入redis分布式锁的实现，如有需要请自行实现。
 */
public class WxCpRedisConfigImpl implements WxCpConfigStorage {
    private static final String ACCESS_TOKEN_KEY = "WX_CP_ACCESS_TOKEN";
    private static final String ACCESS_TOKEN_EXPIRES_TIME_KEY = "WX_CP_ACCESS_TOKEN_EXPIRES_TIME";
    private static final String JS_API_TICKET_KEY = "WX_CP_JS_API_TICKET";
    private static final String JS_API_TICKET_EXPIRES_TIME_KEY = "WX_CP_JS_API_TICKET_EXPIRES_TIME";
    private static final String AGENT_JSAPI_TICKET_KEY = "WX_CP_AGENT_%s_JSAPI_TICKET";
    private static final String AGENT_JSAPI_TICKET_EXPIRES_TIME_KEY = "WX_CP_AGENT_%s_JSAPI_TICKET_EXPIRES_TIME";

    private final JedisPool jedisPool;
    private volatile String corpId;
    private volatile String corpSecret;
    private volatile String token;
    private volatile String aesKey;
    private volatile Integer agentId;
    private volatile String oauth2redirectUri;
    private volatile String httpProxyHost;
    private volatile int httpProxyPort;
    private volatile String httpProxyUsername;
    private volatile String httpProxyPassword;
    private volatile File tmpDirFile;
    private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

    protected volatile String baseApiUrl;

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

    public WxCpRedisConfigImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public WxCpRedisConfigImpl(String host, int port) {
        jedisPool = new JedisPool(host, port);
    }

    public WxCpRedisConfigImpl(JedisPoolConfig poolConfig, String host, int port) {
        jedisPool = new JedisPool(poolConfig, host, port);
    }

    public WxCpRedisConfigImpl(JedisPoolConfig poolConfig, String host, int port, int timeout, String password) {
        jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
    }

    public WxCpRedisConfigImpl(JedisPoolConfig poolConfig, String host, int port, int timeout, String password, int database) {
        jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database);
    }

    /**
     * This method will be destroy jedis pool
     */
    public void destroy() {
        this.jedisPool.destroy();
    }

    @Override
    public String getAccessToken() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(ACCESS_TOKEN_KEY);
        }
    }

    @Override
    public Lock getAccessTokenLock() {
        return new ReentrantLock();
    }

    @Override
    public boolean isAccessTokenExpired() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String expiresTimeStr = jedis.get(ACCESS_TOKEN_EXPIRES_TIME_KEY);

            if (expiresTimeStr != null) {
                return System.currentTimeMillis() > Long.parseLong(expiresTimeStr);
            }

            return true;

        }
    }

    @Override
    public void expireAccessToken() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.set(ACCESS_TOKEN_EXPIRES_TIME_KEY, "0");
        }
    }

    @Override
    public synchronized void updateAccessToken(WxAccessToken accessToken) {
        this.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.set(ACCESS_TOKEN_KEY, accessToken);

            jedis.set(ACCESS_TOKEN_EXPIRES_TIME_KEY,
                    (System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L) + "");
        }
    }

    @Override
    public String getJsapiTicket() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(JS_API_TICKET_KEY);
        }
    }

    @Override
    public Lock getJsapiTicketLock() {
        return new ReentrantLock();
    }

    @Override
    public boolean isJsapiTicketExpired() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String expiresTimeStr = jedis.get(JS_API_TICKET_EXPIRES_TIME_KEY);

            if (expiresTimeStr != null) {
                long expiresTime = Long.parseLong(expiresTimeStr);
                return System.currentTimeMillis() > expiresTime;
            }

            return true;
        }
    }

    @Override
    public void expireJsapiTicket() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.set(JS_API_TICKET_EXPIRES_TIME_KEY, "0");
        }
    }

    @Override
    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.set(JS_API_TICKET_KEY, jsapiTicket);
            jedis.set(JS_API_TICKET_EXPIRES_TIME_KEY,
                    (System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L + ""));
        }

    }

    @Override
    public String getAgentJsapiTicket() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(String.format(AGENT_JSAPI_TICKET_KEY, agentId));
        }
    }

    @Override
    public Lock getAgentJsapiTicketLock() {
        return new ReentrantLock();
    }

    @Override
    public boolean isAgentJsapiTicketExpired() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String expiresTimeStr = jedis.get(String.format(AGENT_JSAPI_TICKET_EXPIRES_TIME_KEY, agentId));

            if (expiresTimeStr != null) {
                return System.currentTimeMillis() > Long.parseLong(expiresTimeStr);
            }

            return true;
        }
    }

    @Override
    public void expireAgentJsapiTicket() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.set(String.format(AGENT_JSAPI_TICKET_EXPIRES_TIME_KEY, agentId), "0");
        }
    }

    @Override
    public void updateAgentJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.set(String.format(AGENT_JSAPI_TICKET_KEY, agentId), jsapiTicket);
            jedis.set(String.format(AGENT_JSAPI_TICKET_EXPIRES_TIME_KEY, agentId),
                    (System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L + ""));
        }

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
    public Integer getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public long getExpiresTime() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String expiresTimeStr = jedis.get(ACCESS_TOKEN_EXPIRES_TIME_KEY);

            if (expiresTimeStr != null) {
                return Long.parseLong(expiresTimeStr);
            }

            return 0L;

        }
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

    // ============================ Setters below

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
