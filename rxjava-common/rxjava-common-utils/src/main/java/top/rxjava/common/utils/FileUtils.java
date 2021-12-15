package top.rxjava.common.utils;

import java.io.Serializable;
import java.util.Objects;

/**
 * 文件帮助类
 * @author happy
 */
public class FileUtils implements Serializable {

    public static String getClassFileUrl(String fileName) {
        return Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(fileName)).getPath();
    }

    private FileUtils() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    public static FileUtils getInstance() {
        return LazyHolder.lazy();
    }

    /**
     * 懒加载
     */
    private static class LazyHolder {
        private static FileUtils lazy() {
            return new FileUtils();
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy();
    }
}
