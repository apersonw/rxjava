package top.rxjava.third.weixin.cp.util.crypto;

import org.apache.commons.codec.binary.Base64;
import top.rxjava.third.weixin.common.util.crypto.WxCryptUtil;
import top.rxjava.third.weixin.cp.config.WxCpTpConfigStorage;

/**
 */
public class WxCpTpCryptUtil extends WxCryptUtil {
    /**
     * 构造函数.
     */
    public WxCpTpCryptUtil(WxCpTpConfigStorage wxCpTpConfigStorage) {
        /*
         * @param token          公众平台上，开发者设置的token
         * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
         * @param appidOrCorpid          公众平台corpId
         */
        String encodingAesKey = wxCpTpConfigStorage.getAesKey();
        String token = wxCpTpConfigStorage.getToken();
        String corpId = wxCpTpConfigStorage.getCorpId();

        this.token = token;
        this.appidOrCorpid = corpId;
        this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }


}
