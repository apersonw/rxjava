package org.rxjava.third.tencent.weixin.miniapp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.SignUtils;
import org.rxjava.third.tencent.weixin.miniapp.api.WxMaService;
import org.rxjava.third.tencent.weixin.miniapp.api.WxMaUserService;
import org.rxjava.third.tencent.weixin.miniapp.bean.WxMaJscode2SessionResult;
import org.rxjava.third.tencent.weixin.miniapp.bean.WxMaPhoneNumberInfo;
import org.rxjava.third.tencent.weixin.miniapp.bean.WxMaUserInfo;
import org.rxjava.third.tencent.weixin.miniapp.config.WxMaConfig;
import org.rxjava.third.tencent.weixin.miniapp.util.crypt.WxMaCryptUtils;

import java.util.Map;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@AllArgsConstructor
public class WxMaUserServiceImpl implements WxMaUserService {
    private WxMaService service;

    @Override
    public WxMaJscode2SessionResult getSessionInfo(String jsCode) throws WxErrorException {
        return service.jsCode2SessionInfo(jsCode);
    }

    @Override
    public WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) {
        return WxMaUserInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
    }

    @Override
    public void setUserStorage(Map<String, String> kvMap, String sessionKey, String openid) throws WxErrorException {
        final WxMaConfig config = this.service.getWxMaConfig();
        JsonObject param = new JsonObject();
        JsonArray array = new JsonArray();
        for (Map.Entry<String, String> e : kvMap.entrySet()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", e.getKey());
            jsonObject.addProperty("value", e.getValue());
            array.add(jsonObject);
        }
        param.add("kv_list", array);
        String params = param.toString();
        String signature = SignUtils.createHmacSha256Sign(params, sessionKey);
        String url = String.format(SET_USER_STORAGE, config.getAppid(), signature, openid, "hmac_sha256");
        this.service.post(url, params);
    }

    @Override
    public WxMaPhoneNumberInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
        return WxMaPhoneNumberInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
    }

    @Override
    public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
        final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
        return generatedSignature.equals(signature);
    }

}
