package org.rxjava.spring.utils;

import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author happy
 * 证书生成帮助类
 */
public class CertUtils implements Serializable {
    /**
     * fileName:相对路径classes
     */
    public String fileToBase64String(String fileName) {
        File file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).getFile());
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 证书字符串
        return Base64Utils.encodeToUrlSafeString(bytes);
    }

    /**
     * 获取证书绝对路径
     *
     * @param channelAppId 渠道AppId
     * @param certFullName 证书全名，包括证书后缀
     * @param encodeString 数据库保存的编码后字符串
     */
    public static String getCertPath(String channelAppId, String certFullName, String encodeString) {
        byte[] bytes = Base64Utils.decodeFromUrlSafeString(encodeString);
        String resultPath = null;
        try {
            String path = FileUtils.getClassFileUrl(".");
            path = path.replace("/D:/", "D:/");
            Path certPath = Paths.get(path, channelAppId);
            if (Files.notExists(certPath)) {
                Files.createDirectory(certPath);
            }
            Path write = Files.write(Paths.get(path, channelAppId, certFullName), bytes);
            resultPath = write.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultPath;
    }

    private CertUtils() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    public static CertUtils getInstance() {
        return LazyHolder.lazy();
    }

    /**
     * 懒加载
     */
    private static class LazyHolder {
        private static CertUtils lazy() {
            return new CertUtils();
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy();
    }
}
