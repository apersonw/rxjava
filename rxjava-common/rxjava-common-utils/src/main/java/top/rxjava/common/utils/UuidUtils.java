package top.rxjava.common.utils;

import java.io.Serializable;
import java.util.Base64;
import java.util.UUID;

/**
 * UUID帮助类
 * @author happy
 */
public class UuidUtils implements Serializable {
    public static String randomUuidToBase64() {
        UUID uuid = UUID.randomUUID();
        byte[] uuidArr = asByteArray(uuid);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(uuidArr);
    }

    public static byte[] asByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }
        return buffer;
    }

    public UUID toUuid(byte[] byteArray) {

        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (byteArray[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (byteArray[i] & 0xff);
        }
        return new UUID(msb, lsb);
    }

    private UuidUtils() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    private static UuidUtils getInstance() {
        return LazyHolder.lazy();
    }

    /**
     * 懒加载
     */
    private static class LazyHolder {
        private static UuidUtils lazy() {
            return new UuidUtils();
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy();
    }
}