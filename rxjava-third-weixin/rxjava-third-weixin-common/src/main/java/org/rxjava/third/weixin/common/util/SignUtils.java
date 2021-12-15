package top.rxjava.third.weixin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具类
 */
@Slf4j
public class SignUtils {
    /**
     * HmacSHA256 签名算法
     *
     * @param message 签名数据
     * @param key     签名密钥
     */
    public static String createHmacSha256Sign(String message, String key) {
        try {
            Mac sha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256.init(secretKeySpec);
            byte[] bytes = sha256.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(bytes).toUpperCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            SignUtils.log.error(e.getMessage(), e);
        }

        return null;
    }
}
