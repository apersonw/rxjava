package org.rxjava.third.tencent.weixin.wxpay.v3.auth;

import java.security.*;
import java.util.Base64;

public class PrivateKeySigner implements Signer {
    private String certificateSerialNumber;

    private PrivateKey privateKey;

    public PrivateKeySigner(String serialNumber, PrivateKey privateKey) {
        this.certificateSerialNumber = serialNumber;
        this.privateKey = privateKey;
    }

    @Override
    public SignatureResult sign(byte[] message) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(message);

            return new SignatureResult(
                    Base64.getEncoder().encodeToString(sign.sign()), certificateSerialNumber);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的私钥", e);
        }
    }
}
