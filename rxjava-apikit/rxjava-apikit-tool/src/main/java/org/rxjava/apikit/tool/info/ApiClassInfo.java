package org.rxjava.apikit.tool.info;

import org.rxjava.apikit.core.HttpMethodType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author happy
 * Api类信息
 */
public class ApiClassInfo extends CommonClassInfo {
    /**
     * url作为key的map
     */
    private final Map<String, Map<HttpMethodType, ApiMethodInfo>> methodUrlMap = new HashMap<>();
    /**
     * 方法名methodName作为key的map
     */
    private final Map<String, ApiMethodInfo> methodNameMap = new HashMap<>();
    /**
     * api方法列表
     */
    private final List<ApiMethodInfo> apiMethodList = new ArrayList<>();

    /**
     * 添加api方法信息
     */
    public void addApiMethod(ApiMethodInfo apiMethodInfo) {
        Map<HttpMethodType, ApiMethodInfo> map = methodUrlMap.computeIfAbsent(apiMethodInfo.getUrl(), k -> new HashMap<>());
        if (map.put(apiMethodInfo.getType(), apiMethodInfo) != null) {
            throw new RuntimeException(apiMethodInfo + "apiMethodInfo严重错误,重复的定义:url" + apiMethodInfo.getUrl() + ",type:" + apiMethodInfo.getType());
        }
        if (methodNameMap.put(apiMethodInfo.getMethodName(), apiMethodInfo) != null) {
            throw new RuntimeException(apiMethodInfo + "apiMethodInfo严重错误,重复的函数名称" + apiMethodInfo.getMethodName() + ",type:" + apiMethodInfo.getType());
        }
        apiMethodInfo.setIndex(apiMethodList.size());
        apiMethodList.add(apiMethodInfo);
    }

    public List<ApiMethodInfo> getApiMethodList() {
        return apiMethodList;
    }
}
