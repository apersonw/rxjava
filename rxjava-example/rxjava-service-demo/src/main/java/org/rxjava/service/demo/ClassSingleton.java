package org.rxjava.service.demo;

import java.io.Serializable;

public class ClassSingleton implements Serializable {

    private ClassSingleton() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    public static ClassSingleton getInstance() {
        return LazyHolder.LAZY;
    }

    private static class LazyHolder {
        private static final ClassSingleton LAZY = new ClassSingleton();
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.LAZY;
    }

}
