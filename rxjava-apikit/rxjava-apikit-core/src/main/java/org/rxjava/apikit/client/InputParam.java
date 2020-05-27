package org.rxjava.apikit.client;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class InputParam {
    private List<Map.Entry<String, Object>> paramList;
    private Object jsonBody;
}
