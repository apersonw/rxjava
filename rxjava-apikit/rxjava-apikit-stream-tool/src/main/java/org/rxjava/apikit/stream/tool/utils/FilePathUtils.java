package org.rxjava.apikit.stream.tool.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件路径帮助类
 */
public class FilePathUtils {
    /**
     * 获取源码文件绝对路径
     */
    public static Path getSourceCodeAbsolutePath(String srcMainJavaPath, Class cls) {
        Package clsPackage = cls.getPackage();
        String[] split = clsPackage.getName().split("\\.");
        return Paths.get(srcMainJavaPath, split).resolve(cls.getSimpleName() + ".java");
    }
}
