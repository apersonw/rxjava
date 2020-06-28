package org.rxjava.third.weixin.pay.v3.auth;

public interface Signer {
    SignatureResult sign(byte[] message);

    class SignatureResult {
        String sign;
        String certificateSerialNumber;

        public SignatureResult(String sign, String serialNumber) {
            this.sign = sign;
            this.certificateSerialNumber = serialNumber;
        }
    }
}
