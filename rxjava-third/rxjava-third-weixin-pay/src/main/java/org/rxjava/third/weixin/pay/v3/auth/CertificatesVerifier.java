package org.rxjava.third.weixin.pay.v3.auth;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class CertificatesVerifier implements Verifier {
    private final HashMap<BigInteger, X509Certificate> certificates = new HashMap<>();

    public CertificatesVerifier(List<X509Certificate> list) {

        for (X509Certificate item : list) {
            certificates.put(item.getSerialNumber(), item);
        }
    }

    private boolean verify(X509Certificate certificate, byte[] message, String signature) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(certificate);
            sign.update(message);
            return sign.verify(Base64.getDecoder().decode(signature));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名验证过程发生了错误", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }

    @Override
    public boolean verify(String serialNumber, byte[] message, String signature) {
        BigInteger val = new BigInteger(serialNumber, 16);
        return certificates.containsKey(val) && verify(certificates.get(val), message, signature);
    }
}
