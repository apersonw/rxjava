package org.rxjava.third.weixin.miniapp.util.crypt;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.rxjava.third.weixin.common.util.crypto.PKCS7Encoder;
import org.rxjava.third.weixin.miniapp.config.WxMaConfig;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

/**
 *
 */
public class WxMaCryptUtils extends org.rxjava.third.weixin.common.util.crypto.WxCryptUtil {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public WxMaCryptUtils(WxMaConfig config) {
        this.appidOrCorpid = config.getAppid();
        this.token = config.getToken();
        this.aesKey = Base64.decodeBase64(config.getAesKey() + "=");
    }

    /**
     * AES解密.
     *
     * @param sessionKey    session_key
     * @param encryptedData 消息密文
     * @param ivStr         iv字符串
     */
    public static String decrypt(String sessionKey, String encryptedData, String ivStr) {
        try {
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(Base64.decodeBase64(ivStr)));

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES"), params);

            return new String(PKCS7Encoder.decode(cipher.doFinal(Base64.decodeBase64(encryptedData))), UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败！", e);
        }
    }


    /**
     * AES解密.
     *
     * @param sessionKey    session_key
     * @param encryptedData 消息密文
     * @param ivStr         iv字符串
     */
    public static String decryptAnotherWay(String sessionKey, String encryptedData, String ivStr) {
        byte[] keyBytes = Base64.decodeBase64(sessionKey.getBytes(UTF_8));

        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }

        Security.addProvider(new BouncyCastleProvider());
        Key key = new SecretKeySpec(keyBytes, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Base64.decodeBase64(ivStr.getBytes(UTF_8))));
            return new String(cipher.doFinal(Base64.decodeBase64(encryptedData.getBytes(UTF_8))), UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败！", e);
        }
    }

}
