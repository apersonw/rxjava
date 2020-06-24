package org.rxjava.third.tencent.weixin.cp.config.impl;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.rxjava.third.tencent.weixin.common.bean.WxAccessToken;
import org.rxjava.third.tencent.weixin.common.redis.RedissonWxRedisOps;
import org.rxjava.third.tencent.weixin.common.redis.WxRedisOps;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 基于Redisson的实现
 *
 * @author yuanqixun
 * @date 2020/5/13
 */
public class WxCpRedissonConfigImpl extends WxCpDefaultConfigImpl {
    protected final static String LOCK_KEY = "wechat_cp_lock:";
    protected final static String CP_ACCESS_TOKEN_KEY = "wechat_cp_access_token_key:";
    protected final static String CP_JSAPI_TICKET_KEY = "wechat_cp_jsapi_ticket_key:";
    protected final static String CP_AGENT_JSAPI_TICKET_KEY = "wechat_cp_agent_jsapi_ticket_key:";

    /**
     * redis 存储的 key 的前缀，可为空
     */
    protected String keyPrefix;
    protected String accessTokenKey;
    protected String jsapiTicketKey;
    protected String agentJsapiTicketKey;
    protected String lockKey;

    private final WxRedisOps redisOps;

    public WxCpRedissonConfigImpl(@NonNull RedissonClient redissonClient, String keyPrefix) {
        this(new RedissonWxRedisOps(redissonClient), keyPrefix);
    }

    public WxCpRedissonConfigImpl(@NonNull RedissonClient redissonClient) {
        this(redissonClient, null);
    }

    private WxCpRedissonConfigImpl(@NonNull WxRedisOps redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.keyPrefix = keyPrefix;
    }

    /**
     * 设置企业微信自研应用ID（整数）,同时初始化相关的redis key，注意要先调用setCorpId，再调用setAgentId
     *
     * @param agentId
     */
    @Override
    public void setAgentId(Integer agentId) {
        super.setAgentId(agentId);
        String ukey = getCorpId().concat(":").concat(String.valueOf(agentId));
        String prefix = StringUtils.isBlank(keyPrefix) ? "" :
                (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
        lockKey = prefix + LOCK_KEY.concat(ukey);
        accessTokenKey = prefix + CP_ACCESS_TOKEN_KEY.concat(ukey);
        jsapiTicketKey = prefix + CP_JSAPI_TICKET_KEY.concat(ukey);
        agentJsapiTicketKey = prefix + CP_AGENT_JSAPI_TICKET_KEY.concat(ukey);
    }

    protected Lock getLockByKey(String key) {
        return redisOps.getLock(key);
    }

    @Override
    public Lock getAccessTokenLock() {
        return getLockByKey(this.lockKey.concat(":").concat("accessToken"));
    }

    @Override
    public Lock getAgentJsapiTicketLock() {
        return getLockByKey(this.lockKey.concat(":").concat("agentJsapiTicket"));

    }

    @Override
    public Lock getJsapiTicketLock() {
        return getLockByKey(this.lockKey.concat(":").concat("jsapiTicket"));
    }

    @Override
    public String getAccessToken() {
        return redisOps.getValue(this.accessTokenKey);
    }

    @Override
    public boolean isAccessTokenExpired() {
        Long expire = redisOps.getExpire(this.accessTokenKey);
        return expire == null || expire < 2;
    }

    @Override
    public void updateAccessToken(WxAccessToken accessToken) {
        redisOps.setValue(this.accessTokenKey, accessToken.getAccessToken(), accessToken.getExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public void updateAccessToken(String accessToken, int expiresInSeconds) {
        redisOps.setValue(this.accessTokenKey, accessToken, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void expireAccessToken() {
        redisOps.expire(this.accessTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public String getJsapiTicket() {
        return redisOps.getValue(this.jsapiTicketKey);
    }

    @Override
    public boolean isJsapiTicketExpired() {
        Long expire = redisOps.getExpire(this.jsapiTicketKey);
        return expire == null || expire < 2;
    }

    @Override
    public void expireJsapiTicket() {
        redisOps.expire(this.jsapiTicketKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        redisOps.setValue(this.jsapiTicketKey, jsapiTicket, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void expireAgentJsapiTicket() {
        redisOps.expire(this.agentJsapiTicketKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateAgentJsapiTicket(String agentJsapiTicket, int expiresInSeconds) {
        redisOps.setValue(this.agentJsapiTicketKey, agentJsapiTicket, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String getAgentJsapiTicket() {
        return redisOps.getValue(this.agentJsapiTicketKey);
    }

    @Override
    public boolean isAgentJsapiTicketExpired() {
        Long expire = redisOps.getExpire(this.agentJsapiTicketKey);
        return expire == null || expire < 2;
    }

}
