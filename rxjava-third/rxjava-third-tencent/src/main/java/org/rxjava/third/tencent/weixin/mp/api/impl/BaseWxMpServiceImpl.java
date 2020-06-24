package org.rxjava.third.tencent.weixin.mp.api.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.bean.WxAccessToken;
import org.rxjava.third.tencent.weixin.common.bean.WxJsapiSignature;
import org.rxjava.third.tencent.weixin.common.bean.WxNetCheckResult;
import org.rxjava.third.tencent.weixin.common.enums.TicketType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.session.StandardSessionManager;
import org.rxjava.third.tencent.weixin.common.session.WxSessionManager;
import org.rxjava.third.tencent.weixin.common.util.DataUtils;
import org.rxjava.third.tencent.weixin.common.util.RandomUtils;
import org.rxjava.third.tencent.weixin.common.util.crypto.SHA1;
import org.rxjava.third.tencent.weixin.common.util.http.*;
import org.rxjava.third.tencent.weixin.common.util.json.WxGsonBuilder;
import org.rxjava.third.tencent.weixin.mp.api.*;
import org.rxjava.third.tencent.weixin.mp.bean.WxMpSemanticQuery;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpCurrentAutoReplyInfo;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpSemanticQueryResult;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpUser;
import org.rxjava.third.tencent.weixin.mp.config.WxMpConfigStorage;
import org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl;
import org.rxjava.third.tencent.weixin.mp.util.WxMpConfigStorageHolder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.Other.*;

/**
 * 基础实现类.
 */
@Slf4j
public abstract class BaseWxMpServiceImpl<H, P> implements WxMpService, RequestHttp<H, P> {
    private static final JsonParser JSON_PARSER = new JsonParser();

    protected WxSessionManager sessionManager = new StandardSessionManager();
    private WxMpKefuService kefuService = new WxMpKefuServiceImpl(this);
    private WxMpMaterialService materialService = new WxMpMaterialServiceImpl(this);
    private WxMpMenuService menuService = new WxMpMenuServiceImpl(this);
    private WxMpUserService userService = new WxMpUserServiceImpl(this);
    private WxMpUserTagService tagService = new WxMpUserTagServiceImpl(this);
    private WxMpQrcodeService qrCodeService = new WxMpQrcodeServiceImpl(this);
    private WxMpCardService cardService = new WxMpCardServiceImpl(this);
    private WxMpStoreService storeService = new WxMpStoreServiceImpl(this);
    private WxMpDataCubeService dataCubeService = new WxMpDataCubeServiceImpl(this);
    private WxMpUserBlacklistService blackListService = new WxMpUserBlacklistServiceImpl(this);
    private WxMpTemplateMsgService templateMsgService = new WxMpTemplateMsgServiceImpl(this);
    private final WxMpSubscribeMsgService subscribeMsgService = new WxMpSubscribeMsgServiceImpl(this);
    private WxMpDeviceService deviceService = new WxMpDeviceServiceImpl(this);
    private WxMpShakeService shakeService = new WxMpShakeServiceImpl(this);
    private WxMpMemberCardService memberCardService = new WxMpMemberCardServiceImpl(this);
    private WxMpMassMessageService massMessageService = new WxMpMassMessageServiceImpl(this);
    private WxMpAiOpenService aiOpenService = new WxMpAiOpenServiceImpl(this);
    private final WxMpWifiService wifiService = new WxMpWifiServiceImpl(this);
    private WxMpMarketingService marketingService = new WxMpMarketingServiceImpl(this);
    private WxMpCommentService commentService = new WxMpCommentServiceImpl(this);
    private WxMpOcrService ocrService = new WxMpOcrServiceImpl(this);
    private WxMpImgProcService imgProcService = new WxMpImgProcServiceImpl(this);

    @Getter
    @Setter
    private WxMpMerchantInvoiceService merchantInvoiceService = new WxMpMerchantInvoiceServiceImpl(this, this.cardService);

    private Map<String, WxMpConfigStorage> configStorageMap;

    private int retrySleepMillis = 1000;
    private int maxRetryTimes = 5;

    @Override
    public boolean checkSignature(String timestamp, String nonce, String signature) {
        try {
            return SHA1.gen(this.getWxMpConfigStorage().getToken(), timestamp, nonce)
                    .equals(signature);
        } catch (Exception e) {
            log.error("Checking signature failed, and the reason is :" + e.getMessage());
            return false;
        }
    }

    @Override
    public String getTicket(TicketType type) throws WxErrorException {
        return this.getTicket(type, false);
    }

    @Override
    public String getTicket(TicketType type, boolean forceRefresh) throws WxErrorException {
        Lock lock = this.getWxMpConfigStorage().getTicketLock(type);
        try {
            lock.lock();
            if (forceRefresh) {
                this.getWxMpConfigStorage().expireTicket(type);
            }

            if (this.getWxMpConfigStorage().isTicketExpired(type)) {
                String responseContent = execute(SimpleGetRequestExecutor.create(this),
                        GET_TICKET_URL.getUrl(this.getWxMpConfigStorage()) + type.getCode(), null);
                JsonObject tmpJsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
                String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
                int expiresInSeconds = tmpJsonObject.get("expires_in").getAsInt();
                this.getWxMpConfigStorage().updateTicket(type, jsapiTicket, expiresInSeconds);
            }
        } finally {
            lock.unlock();
        }

        return this.getWxMpConfigStorage().getTicket(type);
    }

    @Override
    public String getJsapiTicket() throws WxErrorException {
        return this.getJsapiTicket(false);
    }

    @Override
    public String getJsapiTicket(boolean forceRefresh) throws WxErrorException {
        return this.getTicket(TicketType.JSAPI, forceRefresh);
    }

    @Override
    public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException {
        long timestamp = System.currentTimeMillis() / 1000;
        String randomStr = RandomUtils.getRandomStr();
        String jsapiTicket = getJsapiTicket(false);
        String signature = SHA1.genWithAmple("jsapi_ticket=" + jsapiTicket,
                "noncestr=" + randomStr, "timestamp=" + timestamp, "url=" + url);
        WxJsapiSignature jsapiSignature = new WxJsapiSignature();
        jsapiSignature.setAppId(this.getWxMpConfigStorage().getAppId());
        jsapiSignature.setTimestamp(timestamp);
        jsapiSignature.setNonceStr(randomStr);
        jsapiSignature.setUrl(url);
        jsapiSignature.setSignature(signature);
        return jsapiSignature;
    }

    @Override
    public String getAccessToken() throws WxErrorException {
        return getAccessToken(false);
    }

    @Override
    public String shortUrl(String longUrl) throws WxErrorException {
        if (longUrl.contains("&access_token=")) {
            throw new WxErrorException(WxError.builder().errorCode(-1)
                    .errorMsg("要转换的网址中存在非法字符｛&access_token=｝，会导致微信接口报错，属于微信bug，请调整地址，否则不建议使用此方法！")
                    .build());
        }

        JsonObject o = new JsonObject();
        o.addProperty("action", "long2short");
        o.addProperty("long_url", longUrl);
        String responseContent = this.post(SHORTURL_API_URL, o.toString());
        JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
        return tmpJsonElement.getAsJsonObject().get("short_url").getAsString();
    }

    @Override
    public WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery) throws WxErrorException {
        String responseContent = this.post(SEMANTIC_SEMPROXY_SEARCH_URL, semanticQuery.toJson());
        return WxMpSemanticQueryResult.fromJson(responseContent);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        return String.format(CONNECT_OAUTH2_AUTHORIZE_URL.getUrl(this.getWxMpConfigStorage()),
                this.getWxMpConfigStorage().getAppId(), URIUtil.encodeURIComponent(redirectURI), scope, StringUtils.trimToEmpty(state));
    }

    @Override
    public String buildQrConnectUrl(String redirectURI, String scope, String state) {
        return String.format(QRCONNECT_URL.getUrl(this.getWxMpConfigStorage()), this.getWxMpConfigStorage().getAppId(),
                URIUtil.encodeURIComponent(redirectURI), scope, StringUtils.trimToEmpty(state));
    }

    private WxMpOAuth2AccessToken getOAuth2AccessToken(String url) throws WxErrorException {
        try {
            RequestExecutor<String, String> executor = SimpleGetRequestExecutor.create(this);
            String responseText = executor.execute(url, null, WxType.MP);
            return WxMpOAuth2AccessToken.fromJson(responseText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
        String url = String.format(OAUTH2_ACCESS_TOKEN_URL.getUrl(this.getWxMpConfigStorage()), this.getWxMpConfigStorage().getAppId(),
                this.getWxMpConfigStorage().getSecret(), code);
        return this.getOAuth2AccessToken(url);
    }

    @Override
    public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException {
        String url = String.format(OAUTH2_REFRESH_TOKEN_URL.getUrl(this.getWxMpConfigStorage()), this.getWxMpConfigStorage().getAppId(), refreshToken);
        return this.getOAuth2AccessToken(url);
    }

    @Override
    public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken token, String lang) throws WxErrorException {
        if (lang == null) {
            lang = "zh_CN";
        }

        String url = String.format(OAUTH2_USERINFO_URL.getUrl(this.getWxMpConfigStorage()), token.getAccessToken(), token.getOpenId(), lang);

        try {
            RequestExecutor<String, String> executor = SimpleGetRequestExecutor.create(this);
            String responseText = executor.execute(url, null, WxType.MP);
            return WxMpUser.fromJson(responseText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean oauth2validateAccessToken(WxMpOAuth2AccessToken token) {
        String url = String.format(OAUTH2_VALIDATE_TOKEN_URL.getUrl(this.getWxMpConfigStorage()), token.getAccessToken(), token.getOpenId());

        try {
            SimpleGetRequestExecutor.create(this).execute(url, null, WxType.MP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WxErrorException e) {
            return false;
        }
        return true;
    }

    @Override
    public String[] getCallbackIP() throws WxErrorException {
        String responseContent = this.get(GET_CALLBACK_IP_URL, null);
        JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
        JsonArray ipList = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();
        String[] ipArray = new String[ipList.size()];
        for (int i = 0; i < ipList.size(); i++) {
            ipArray[i] = ipList.get(i).getAsString();
        }
        return ipArray;
    }

    @Override
    public WxNetCheckResult netCheck(String action, String operator) throws WxErrorException {
        JsonObject o = new JsonObject();
        o.addProperty("action", action);
        o.addProperty("check_operator", operator);
        String responseContent = this.post(NETCHECK_URL, o.toString());
        return WxNetCheckResult.fromJson(responseContent);
    }

    @Override
    public WxMpCurrentAutoReplyInfo getCurrentAutoReplyInfo() throws WxErrorException {
        return WxMpCurrentAutoReplyInfo.fromJson(this.get(GET_CURRENT_AUTOREPLY_INFO_URL, null));
    }

    @Override
    public void clearQuota(String appid) throws WxErrorException {
        JsonObject o = new JsonObject();
        o.addProperty("appid", appid);
        this.post(CLEAR_QUOTA_URL, o.toString());
    }

    @Override
    public String get(String url, String queryParam) throws WxErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String get(WxMpApiUrl url, String queryParam) throws WxErrorException {
        return this.get(url.getUrl(this.getWxMpConfigStorage()), queryParam);
    }

    @Override
    public String post(String url, String postData) throws WxErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }

    @Override
    public String post(WxMpApiUrl url, String postData) throws WxErrorException {
        return this.post(url.getUrl(this.getWxMpConfigStorage()), postData);
    }

    @Override
    public String post(String url, Object obj) throws WxErrorException {
        return this.execute(SimplePostRequestExecutor.create(this), url, WxGsonBuilder.create().toJson(obj));
    }

    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, WxMpApiUrl url, E data) throws WxErrorException {
        return this.execute(executor, url.getUrl(this.getWxMpConfigStorage()), data);
    }

    /**
     * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求.
     */
    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        int retryTimes = 0;
        do {
            try {
                return this.executeInternal(executor, uri, data);
            } catch (WxErrorException e) {
                if (retryTimes + 1 > this.maxRetryTimes) {
                    log.warn("重试达到最大次数【{}】", maxRetryTimes);
                    //最后一次重试失败后，直接抛出异常，不再等待
                    throw new RuntimeException("微信服务端异常，超出重试次数");
                }

                WxError error = e.getError();
                // -1 系统繁忙, 1000ms后重试
                if (error.getErrorCode() == -1) {
                    int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
                    try {
                        log.warn("微信系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                } else {
                    throw e;
                }
            }
        } while (retryTimes++ < this.maxRetryTimes);

        log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
        throw new RuntimeException("微信服务端异常，超出重试次数");
    }

    protected <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
        E dataForLog = DataUtils.handleDataWithSecret(data);

        if (uri.contains("access_token=")) {
            throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
        }

        String accessToken = getAccessToken(false);
        String uriWithAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token=" + accessToken;

        try {
            T result = executor.execute(uriWithAccessToken, data, WxType.MP);
            log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uriWithAccessToken, dataForLog, result);
            return result;
        } catch (WxErrorException e) {
            WxError error = e.getError();
            /*
             * 发生以下情况时尝试刷新access_token
             * 40001 获取 access_token 时 AppSecret 错误，或者 access_token 无效。请开发者认真比对 AppSecret 的正确性，或查看是否正在为恰当的公众号调用接口
             * 42001 access_token 超时，请检查 access_token 的有效期，请参考基础支持 - 获取 access_token 中，对 access_token 的详细机制说明
             * 40014 不合法的 access_token ，请开发者认真比对 access_token 的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
             */
            if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001 || error.getErrorCode() == 40014) {
                // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
                Lock lock = this.getWxMpConfigStorage().getAccessTokenLock();
                lock.lock();
                try {
                    if (StringUtils.equals(this.getWxMpConfigStorage().getAccessToken(), accessToken)) {
                        this.getWxMpConfigStorage().expireAccessToken();
                    }
                } catch (Exception ex) {
                    this.getWxMpConfigStorage().expireAccessToken();
                } finally {
                    lock.unlock();
                }
                if (this.getWxMpConfigStorage().autoRefreshToken()) {
                    return this.execute(executor, uri, data);
                }
            }

            if (error.getErrorCode() != 0) {
                log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uriWithAccessToken, dataForLog, error);
                throw new WxErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uriWithAccessToken, dataForLog, e.getMessage());
            throw new WxErrorException(WxError.builder().errorMsg(e.getMessage()).build(), e);
        }
    }

    @Override
    public WxMpConfigStorage getWxMpConfigStorage() {
        if (this.configStorageMap.size() == 1) {
            // 只有一个公众号，直接返回其配置即可
            return this.configStorageMap.values().iterator().next();
        }

        return this.configStorageMap.get(WxMpConfigStorageHolder.get());
    }

    protected String extractAccessToken(String resultContent) throws WxErrorException {
        WxMpConfigStorage config = this.getWxMpConfigStorage();
        WxError error = WxError.fromJson(resultContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }
        WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
        config.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
        return config.getAccessToken();
    }

    @Override
    public void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider) {
        final String defaultMpId = WxMpConfigStorageHolder.get();
        this.setMultiConfigStorages(ImmutableMap.of(defaultMpId, wxConfigProvider), defaultMpId);
    }

    @Override
    public void setMultiConfigStorages(Map<String, WxMpConfigStorage> configStorages) {
        this.setMultiConfigStorages(configStorages, configStorages.keySet().iterator().next());
    }

    @Override
    public void setMultiConfigStorages(Map<String, WxMpConfigStorage> configStorages, String defaultMpId) {
        this.configStorageMap = Maps.newHashMap(configStorages);
        WxMpConfigStorageHolder.set(defaultMpId);
        this.initHttp();
    }

    @Override
    public void addConfigStorage(String mpId, WxMpConfigStorage configStorages) {
        synchronized (this) {
            this.configStorageMap.put(mpId, configStorages);
        }
    }

    @Override
    public void removeConfigStorage(String mpId) {
        synchronized (this) {
            this.configStorageMap.remove(mpId);
        }
    }

    @Override
    public WxMpService switchoverTo(String mpId) {
        if (this.configStorageMap.containsKey(mpId)) {
            WxMpConfigStorageHolder.set(mpId);
            return this;
        }

        throw new RuntimeException(String.format("无法找到对应【%s】的公众号配置信息，请核实！", mpId));
    }

    @Override
    public boolean switchover(String mpId) {
        if (this.configStorageMap.containsKey(mpId)) {
            WxMpConfigStorageHolder.set(mpId);
            return true;
        }

        log.error("无法找到对应【{}】的公众号配置信息，请核实！", mpId);
        return false;
    }

    @Override
    public void setRetrySleepMillis(int retrySleepMillis) {
        this.retrySleepMillis = retrySleepMillis;
    }

    @Override
    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public WxMpKefuService getKefuService() {
        return this.kefuService;
    }

    @Override
    public WxMpMaterialService getMaterialService() {
        return this.materialService;
    }

    @Override
    public WxMpMenuService getMenuService() {
        return this.menuService;
    }

    @Override
    public WxMpUserService getUserService() {
        return this.userService;
    }

    @Override
    public WxMpUserTagService getUserTagService() {
        return this.tagService;
    }

    @Override
    public WxMpQrcodeService getQrcodeService() {
        return this.qrCodeService;
    }

    @Override
    public WxMpCardService getCardService() {
        return this.cardService;
    }

    @Override
    public WxMpDataCubeService getDataCubeService() {
        return this.dataCubeService;
    }

    @Override
    public WxMpUserBlacklistService getBlackListService() {
        return this.blackListService;
    }

    @Override
    public WxMpStoreService getStoreService() {
        return this.storeService;
    }

    @Override
    public WxMpTemplateMsgService getTemplateMsgService() {
        return this.templateMsgService;
    }

    @Override
    public WxMpSubscribeMsgService getSubscribeMsgService() {
        return this.subscribeMsgService;
    }

    @Override
    public WxMpDeviceService getDeviceService() {
        return this.deviceService;
    }

    @Override
    public WxMpShakeService getShakeService() {
        return this.shakeService;
    }

    @Override
    public WxMpMemberCardService getMemberCardService() {
        return this.memberCardService;
    }

    @Override
    public RequestHttp getRequestHttp() {
        return this;
    }

    @Override
    public WxMpMassMessageService getMassMessageService() {
        return this.massMessageService;
    }

    @Override
    public void setKefuService(WxMpKefuService kefuService) {
        this.kefuService = kefuService;
    }

    @Override
    public void setMaterialService(WxMpMaterialService materialService) {
        this.materialService = materialService;
    }

    @Override
    public void setMenuService(WxMpMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void setUserService(WxMpUserService userService) {
        this.userService = userService;
    }

    @Override
    public void setTagService(WxMpUserTagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public void setQrCodeService(WxMpQrcodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Override
    public void setCardService(WxMpCardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void setStoreService(WxMpStoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public void setDataCubeService(WxMpDataCubeService dataCubeService) {
        this.dataCubeService = dataCubeService;
    }

    @Override
    public void setBlackListService(WxMpUserBlacklistService blackListService) {
        this.blackListService = blackListService;
    }

    @Override
    public void setTemplateMsgService(WxMpTemplateMsgService templateMsgService) {
        this.templateMsgService = templateMsgService;
    }

    @Override
    public void setDeviceService(WxMpDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void setShakeService(WxMpShakeService shakeService) {
        this.shakeService = shakeService;
    }

    @Override
    public void setMemberCardService(WxMpMemberCardService memberCardService) {
        this.memberCardService = memberCardService;
    }

    @Override
    public void setMassMessageService(WxMpMassMessageService massMessageService) {
        this.massMessageService = massMessageService;
    }

    @Override
    public WxMpAiOpenService getAiOpenService() {
        return this.aiOpenService;
    }

    @Override
    public void setAiOpenService(WxMpAiOpenService aiOpenService) {
        this.aiOpenService = aiOpenService;
    }

    @Override
    public WxMpWifiService getWifiService() {
        return this.wifiService;
    }

    @Override
    public WxMpOcrService getOcrService() {
        return this.ocrService;
    }

    @Override
    public WxMpMarketingService getMarketingService() {
        return this.marketingService;
    }

    @Override
    public void setMarketingService(WxMpMarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @Override
    public void setOcrService(WxMpOcrService ocrService) {
        this.ocrService = ocrService;
    }

    @Override
    public WxMpCommentService getCommentService() {
        return this.commentService;
    }

    @Override
    public void setCommentService(WxMpCommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public WxMpImgProcService getImgProcService() {
        return this.imgProcService;
    }

    @Override
    public void setImgProcService(WxMpImgProcService imgProcService) {
        this.imgProcService = imgProcService;
    }
}
