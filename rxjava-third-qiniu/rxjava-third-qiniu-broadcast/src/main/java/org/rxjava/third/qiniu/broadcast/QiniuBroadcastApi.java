package top.rxjava.third.qiniu.broadcast;

import java.io.Serializable;

/**
 * 七牛云Api
 */
public class QiniuBroadcastApi implements Serializable {
    private final String accessKey;
    private final String secretKey;
    private final String bucket;

    private QiniuBroadcastApi() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    private QiniuBroadcastApi(String accessKey, String secretKey, String bucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    public static QiniuBroadcastApi getInstance(String accessKey, String secretKey, String bucket) {
        return LazyHolder.lazy(accessKey, secretKey, bucket);
    }

    private static class LazyHolder {
        private static QiniuBroadcastApi lazy(String accessKey, String secretKey, String bucket) {
            return new QiniuBroadcastApi(accessKey, secretKey, bucket);
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy(accessKey, secretKey, bucket);
    }
}
