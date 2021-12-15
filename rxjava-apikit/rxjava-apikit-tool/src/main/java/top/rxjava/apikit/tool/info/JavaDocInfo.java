package top.rxjava.apikit.tool.info;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import java.util.*;
import java.util.stream.Collectors;

/**
 * javadoc 注释信息
 *
 * @author happy
 */
public class JavaDocInfo {

    /**
     * List<Map.Entry<String, List<String>>> 结构是 tagName -> fragments list
     */
    private final ListMultimap<String, List<String>> tags = Multimaps.newListMultimap(
            new LinkedHashMap<>(), ArrayList::new
    );

    /**
     * 获取注释第一行
     */
    public String getFirstRow() {
        if (tags.isEmpty()) {
            return "请设置控制器中文名";
        }
        List<List<String>> tagsList = tags.get(null);
        if (tagsList.isEmpty()) {
            return "请设置控制器中文名";
        }
        List<String> tagValues = tagsList.get(0);
        return tagValues.get(0);
    }

    public void add(String tagName, List<String> fragmentsInfo) {
        tags.put(tagName, fragmentsInfo);
    }

    public List<List<String>> get(String tagName) {
        return tags.get(tagName);
    }

    public String getToString(String tagName, CharSequence delimiter) {
        return getToString(tagName, delimiter, "", "");
    }

    public String getToString(String tagName) {
        return getToString(tagName, " ", "", "");
    }

    public String getToString(String tagName, CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return tags.get(tagName).stream().flatMap(Collection::stream).collect(Collectors.joining(delimiter, prefix, suffix));
    }

    public String getByParamToString(String param, CharSequence delimiter) {
        return getByParamToString(param, delimiter, "", "");
    }

    public String getByParamToString(String param, CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return tags.get("@param").stream()
                .filter(r -> !r.isEmpty() && r.get(0).equals(param))
                .flatMap(item -> item.stream().skip(1))
                .collect(Collectors.joining(delimiter, prefix, suffix));
    }

    public ListMultimap<String, List<String>> getTags() {
        return tags;
    }

    public Map.Entry<String, Collection<List<String>>> getTags(int index) {
        int i = 0;
        for (Map.Entry<String, Collection<List<String>>> entry : tags.asMap().entrySet()) {
            if (i == index) {
                return entry;
            }
            i++;
        }
        return null;
    }
}
