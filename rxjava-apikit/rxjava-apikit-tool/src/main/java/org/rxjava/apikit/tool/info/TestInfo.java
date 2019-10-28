package org.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author happy 2019/10/28 00:16
 */
@Getter
@Setter
public class TestInfo<T> {
    private String id;
    private int num;
    private boolean ok;
    private List<String> list;
    private Map<String, String> map;
    private T a;
}
