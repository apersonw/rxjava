package org.rxjava.apikit.client;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class InputParam {
    /**
     * 参数列表，存放在Url上
     */
    private List<Map.Entry<String, Object>> paramList;
    /**
     * body参数列表，若无jsonBody，则存放在body，若有jsonBody，则合并到paramList
     */
    private List<Map.Entry<String, Object>> bodyParamList;
    /**
     * json格式的body
     */
    private Object jsonBody;
}
