package org.rxjava.apikit.tool.utils;

import org.rxjava.apikit.tool.info.Import;

import java.io.File;

/**
 * 路径帮助类
 *
 * @author happy
 */
public final class LocalPathUtils {

    /**
     * 获取指定路径下文件并修改名称+后缀
     */
    public static File packToPath(String path, String packname, String name, String suffix) {
        File file = packToPath(path, packname);
        return new File(file, name + suffix);
    }

    /**
     * 获取指定路径下文件
     */
    public static File packToPath(String path, String packageName) {
        return new File(path, packageName.replace(".", "/"));
    }

    /**
     * 获取指定路径下文件并修改名称
     */
    public static File packToPath(String path, String packname, String name) {
        File file = packToPath(path, packname);
        return new File(file, name);
    }

    /**
     * 路径转为包格式
     */
    public static String pathToPack(String path) {
        return path.replace("/", ".");
    }

    /**
     * 字符串首字母大写
     */
    public static String toClassName(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (0 == i) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 检查是否在Lang包中的类
     */
    public static Import getLangImport(String name) {
        try {
            Class<?> aClass = Class.forName("java.lang" + "." + name);
            return new Import("java.lang", name, false, false);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
