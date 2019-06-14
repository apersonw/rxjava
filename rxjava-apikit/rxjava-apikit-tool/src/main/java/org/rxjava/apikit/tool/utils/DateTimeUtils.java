package org.rxjava.apikit.tool.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期帮助类
 *
 * @author happy
 */
public class DateTimeUtils {

    static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化日期为字符串
     */
    public static String format(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        dateFormat.setLenient(false);
        return dateFormat.format(date);
    }

    /**
     * 解析日期字符串
     */
    public static Date parse(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        dateFormat.setLenient(false);
        return dateFormat.parse(date);
    }
}
