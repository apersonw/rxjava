package top.rxjava.apikit.client;

import org.apache.commons.lang3.StringUtils;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author happy
 */
public class ApiUtils {
    /**
     * 正则表达式名称
     */
    private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)}");

    public static String expandUriComponent(String url){
        return expandUriComponent(url, new HashMap<>(0));
    }

    /**
     * 展开Uri组件
     */
    public static String expandUriComponent(String url, Map<String, ?> uriVariables) {
        if (StringUtils.isEmpty(url)) {
            return "";
        } else if (url.indexOf(123) == -1) {
            //若是未找到{则直接返回url
            return url;
        } else {
            Matcher matcher = NAMES_PATTERN.matcher(url);

            StringBuffer sb;
            String replacement;
            for (sb = new StringBuffer(); matcher.find(); matcher.appendReplacement(sb, replacement)) {
                String match = matcher.group(1);
                String varName = getVariableName(match);
                Object varValue = uriVariables.get(varName);
                if (varValue == null) {
                    throw new RuntimeException("协议定义错误，需要参数未找到");
                }

                String varValueString = getVarValueAsString(varValue);

                try {
                    replacement = Matcher.quoteReplacement(URLEncoder.encode(varValueString, "utf8"));
                } catch (UnsupportedEncodingException var10) {
                    throw new RuntimeException(var10);
                }
            }

            matcher.appendTail(sb);
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        Map<String, Object> _uriVariables = new HashMap<>();
        _uriVariables.put("id", "testId");
        String _url = ApiUtils.expandUriComponent("mergeTestPath/path/{id}", _uriVariables);
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
    private static String getVarValueAsString(Object variableValue) {
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
