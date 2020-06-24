package org.rxjava.third.tencent.weixin.open.util;

import org.apache.commons.codec.binary.Base64;
import org.rxjava.third.tencent.weixin.open.api.WxOpenConfigStorage;

/**
 */
public class WxOpenCryptUtil extends org.rxjava.third.tencent.weixin.common.util.crypto.WxCryptUtil {
    /**
     * 构造函数
     *
     * @param wxOpenConfigStorage
     */
    public WxOpenCryptUtil(WxOpenConfigStorage wxOpenConfigStorage) {
        /*
         * @param token          公众平台上，开发者设置的token
         * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
         * @param appId          公众平台appid
         */
        String encodingAesKey = wxOpenConfigStorage.getComponentAesKey();
        String token = wxOpenConfigStorage.getComponentToken();
        String appId = wxOpenConfigStorage.getComponentAppId();

        this.token = token;
        this.appidOrCorpid = appId;
        this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }
}
