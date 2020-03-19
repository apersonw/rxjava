package org.rxjava.apikit.tool.utils;

import org.apache.commons.lang3.StringUtils;
import org.rxjava.apikit.tool.info.JavaDocInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author happy
 */
public class CommentUtils {
    /**
     * 获取注释不包含tag的内容
     */
    public static String getCommentNoTag(JavaDocInfo comment, String start) {
        if (comment == null) {
            return start;
        }
        StringBuilder sb = new StringBuilder();

        for (String tagName : comment.getTags().keySet()) {
            if (StringUtils.isEmpty(tagName)) {
                sb.append(start);
                formatCommentItem(comment, start, sb, tagName);
            }
        }
        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return start;
        }
    }

    /**
     * 获取注释不带tabName的内容
     */
    public static String getBaseComment(JavaDocInfo comment, String start) {
        if (comment == null) {
            return start;
        }
        StringBuilder sb = new StringBuilder();

        List<List<String>> lists = comment.get(null);
        lists.stream().flatMap(Collection::stream).forEach(c -> {
            sb.append(start);
            sb.append(StringUtils.trimToEmpty(c).replace("\n", "").replace("\r", ""));
            sb.append("\n");
        });
        String str = StringUtils.stripEnd(sb.toString(), null);
        if (str.length() > 0) {
            return str;
        } else {
            return start;
        }
    }

    /**
     * 格式化注释
     */
    private static void formatCommentItem(JavaDocInfo comment, String start, StringBuilder sb, String tagName) {
        List<List<String>> fragments = comment.getTags().get(tagName);
        sb.append(fragments.stream().flatMap(Collection::stream).collect(Collectors.joining("\n" + start)));
    }

    /**
     * 获取注释
     */
    public static String getComment(JavaDocInfo comment, String start) {
        if (comment == null) {
            return start;
        }
        StringBuilder sb = new StringBuilder();
        for (String tagName : comment.getTags().keySet()) {
            sb.append(start);
            if (StringUtils.isNotEmpty(tagName)) {
                sb.append(tagName);
                sb.append(' ');
            }
            formatCommentItem(comment, start, sb, tagName);
            sb.append("\n");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 转化注释为map
     */
    public static Map<String, String> toMap(JavaDocInfo comment) {
        if (comment == null) {
            return Collections.emptyMap();
        }
        Map<String, String> map = new HashMap<>();

        for (String tagName : comment.getTags().keySet()) {
            StringBuilder sb = new StringBuilder();

            boolean isParam = "@param".equals(tagName);

            Collection<List<String>> fragments = comment.getTags().get(tagName);

            if (isParam) {
                fragments.forEach(fragment -> {
                    if (!fragment.isEmpty()) {
                        String paramName = fragment.get(0);
                        map.put(paramName, fragments.stream().skip(1).flatMap(Collection::stream).collect(Collectors.joining(" ")));
                    }
                });
            } else {
                map.put(tagName, fragments.stream().flatMap(Collection::stream).collect(Collectors.joining(" ")));
            }
        }
        return map;
    }
}
