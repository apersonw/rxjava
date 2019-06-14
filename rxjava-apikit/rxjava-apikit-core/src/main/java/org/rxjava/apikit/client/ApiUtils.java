package org.rxjava.apikit.client;

/**
 * @author happy
 */

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiUtils {
    /**
     * 正则表达式名称
     */
    private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)}");

    /**
     * 展开Uri组件
     */
    public static String expandUriComponent(String url, Map<String, ?> uriVariables) {
        if (url == null) {
            return null;
        } else if (url.indexOf(123) == -1) {
            return url;
        } else {
            Matcher matcher = NAMES_PATTERN.matcher(url);

            StringBuffer sb;
            String replacement;
            for (sb = new StringBuffer(); matcher.find(); matcher.appendReplacement(sb, replacement)) {
                String match = matcher.group(1);
                String variableName = getVariableName(match);
                Object variableValue = uriVariables.get(variableName);
                if (variableValue == null) {
                    throw new RuntimeException("协议定义错误，需要参数未找到");
                }

                String variableValueString = getVariableValueAsString(variableValue);

                try {
                    replacement = Matcher.quoteReplacement(URLEncoder.encode(variableValueString, "utf8"));
                } catch (UnsupportedEncodingException var10) {
                    throw new RuntimeException(var10);
                }
            }

            matcher.appendTail(sb);
            return sb.toString();
        }
    }

    /**
     * 获取变量名
     */
    private static String getVariableName(String match) {
        int colonIdx = match.indexOf(58);
        return colonIdx != -1 ? match.substring(0, colonIdx) : match;
    }

    /**
     * 获取变量值并转化为字符串
     */
    private static String getVariableValueAsString(Object variableValue) {
        if (variableValue != null) {
            if (variableValue instanceof List) {
                List list = (List) variableValue;
                StringBuilder sb = new StringBuilder();

                Object item;
                for (Iterator var3 = list.iterator(); var3.hasNext(); sb.append(item)) {
                    item = var3.next();
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                }

                return sb.toString();
            } else {
                return variableValue.toString();
            }
        } else {
            return "";
        }
    }

    public static ApiType type(Type raw, Type... types) {
        return new ApiType(raw, types);
    }
}
