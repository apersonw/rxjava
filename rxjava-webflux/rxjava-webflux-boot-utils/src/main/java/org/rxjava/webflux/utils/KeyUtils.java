package org.rxjava.webflux.utils;

import java.io.Serializable;

/**
 * @author happy 2019-04-27 00:30
 * Key生成器
 */
public class KeyUtils implements Serializable {

    public String newCacheId(Class<?> clazz, String key) {
        return clazz.getSimpleName() + "-" + key;
    }

    private KeyUtils() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    public static KeyUtils getInstance() {
        return LazyHolder.lazy();
    }

    /**
     * 懒加载
     */
    private static class LazyHolder {
        private static KeyUtils lazy() {
            return new KeyUtils();
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy();
    }
}
