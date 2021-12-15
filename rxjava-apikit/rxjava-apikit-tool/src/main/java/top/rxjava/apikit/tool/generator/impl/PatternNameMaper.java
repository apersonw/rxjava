package top.rxjava.apikit.tool.generator.impl;

import top.rxjava.apikit.tool.generator.NameMaper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author happy
 */
public class PatternNameMaper implements NameMaper {

    /**
     * 一个正则表达式解压需要的信息从服务ID获取
     * "(?<name>.*)-(?<version>v.*$)"
     */
    private Pattern sourcePattern;
    /**
     * 一个正则表达式关联命名分组定义在服务部分
     * "${version}/${name}"
     */
    private String distPattern;

    @Override
    public String apply(String name) {
        Matcher matcher = this.sourcePattern.matcher(name);
        String distName = matcher.replaceFirst(this.distPattern);
        distName = clean(distName);
        return (hasText(distName) ? distName : name);
    }

    public PatternNameMaper(String sourcePattern, String distPattern) {
        this.sourcePattern = Pattern.compile(sourcePattern);
        this.distPattern = distPattern;
    }

    private String clean(final String route) {
        String routeToClean = route.replaceAll("/{2,}", "/");
        if (routeToClean.startsWith("/")) {
            routeToClean = routeToClean.substring(1);
        }
        if (routeToClean.endsWith("/")) {
            routeToClean = routeToClean.substring(0, routeToClean.length() - 1);
        }
        return routeToClean;
    }

    private static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    private static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }
}
