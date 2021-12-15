package top.rxjava.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 字符串脱敏工具
 *
 * @author xyh
 */
public class DesensitizedUtil implements Serializable {

    private final String defaultPrefix = "****";


//    private DesensitizedUtil() {
//        throw new RuntimeException("禁止反射破坏单例");
//    }
//
//    public static DesensitizedUtil getInstance() {
//        return DesensitizedUtil.LazyHolder.lazy();
//    }
//
//    /**
//     * 懒加载
//     */
//    private static class LazyHolder {
//        private static DesensitizedUtil lazy() {
//            return new DesensitizedUtil();
//        }
//    }
//
//    /**
//     * 禁止序列化破坏单例
//     */
//    private Object readResolve() {
//        return DesensitizedUtil.LazyHolder.lazy();
//    }

    /**
     * 默认用*脱敏
     *
     * @param text   需要处理的内容
     * @param start  开始位置
     * @param num    替换数量
     * @param prefix 替换的字符
     * @return
     */
    public String dealString(String text, int start, int num, String prefix) {
        if (start < 0 || num <= 0
                || StringUtils.isBlank(text)
                || start > text.length()) {
            return text;
        }
        if (StringUtils.isBlank(prefix)) {
            prefix = defaultPrefix;
        }
        if (StringUtils.isBlank(prefix)) {
            prefix = defaultPrefix;
        } else {
            prefix = getPrefixText(num);
        }
        start--;
        return StringUtils.join(
                StringUtils.substring(text, 0, start),
                prefix,
                StringUtils.substring(text, start + num));
    }

    private String getPrefixText(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append("*");
        }
        return sb.toString();
    }

    /**
     * 脱敏11位电话号码：从0，第4位开始，替换4个号码
     *
     * @param phone 11位电话号码
     * @return
     */
    public String dealPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return phone;
        }
        return dealString(phone, 4, 4, null);
    }

    public String dealIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return idCard;
        }
        return dealString(idCard, 3, 15, null);
    }

}
