package top.rxjava.third.weixin.pay.v3.auth;

public interface Verifier {
    boolean verify(String serialNumber, byte[] message, String signature);
}
