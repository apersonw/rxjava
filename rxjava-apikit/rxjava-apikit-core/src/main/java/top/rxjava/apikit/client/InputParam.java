package top.rxjava.apikit.client;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class InputParam {
    /**
     * 参数列表，存放在Url上
     */
    private List<Map.Entry<String, Object>> paramList;
    /**
     * json格式的body
     */
    private Object jsonBody;
    /**
     * authorization
     */
    private String authorization;

    /**
     * 增加Form对象
     */
    public void addForm(EncodeObject encodeObject) {
        if (ObjectUtils.isEmpty(paramList)) {
            paramList = new ArrayList<>();
        }
        encodeObject.encode("", paramList);
    }

    /**
     * 增加Form对象
     */
    public void addRequestParam(String name, Object value) {
        if (ObjectUtils.isEmpty(paramList)) {
            paramList = new ArrayList<>();
        }
        paramList.add(new AbstractMap.SimpleImmutableEntry<>(name, value));
    }
}
