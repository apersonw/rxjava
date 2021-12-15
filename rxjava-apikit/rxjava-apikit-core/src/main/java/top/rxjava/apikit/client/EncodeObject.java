package top.rxjava.apikit.client;

import java.util.List;
import java.util.Map;

/**
 * @author wugang
 */
public interface EncodeObject {
    List<Map.Entry<String, Object>> encode(String $parent, List<Map.Entry<String, Object>> $list);
}
