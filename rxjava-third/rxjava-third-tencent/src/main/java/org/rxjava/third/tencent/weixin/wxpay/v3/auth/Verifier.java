package org.rxjava.third.tencent.weixin.wxpay.v3.auth;

public interface Verifier {
    boolean verify(String serialNumber, byte[] message, String signature);
}
