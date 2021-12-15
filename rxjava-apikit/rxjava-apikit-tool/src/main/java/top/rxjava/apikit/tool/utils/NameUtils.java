package top.rxjava.apikit.tool.utils;

/**
 * @author happy
 * 命名帮助类
 */
public class NameUtils {
    /**
     * 首字母小写
     */
    public static String toFieldName(String name) {
        if (!name.isEmpty()) {
            char c = name.charAt(0);
            return Character.toLowerCase(c) + name.substring(1);
        }
        return name;
    }
}
