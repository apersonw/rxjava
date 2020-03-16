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
public class ApiClass extends ClassInfo {
    /**
     * url作为key的map
     */
    private Map<String, Map<HttpMethodType, ApiMethodInfo>> methodUrlMap = new HashMap<>();
    /**
     * 方法名methodName作为key的map
     */
    private Map<String, ApiMethodInfo> methodNameMap = new HashMap<>();
    /**
     * api方法列表
     */
    private List<ApiMethodInfo> methodInfos = new ArrayList<>();
    /**
     * 添加api方法信息
     */
    public void addApiMethod(ApiMethodInfo apiMethodInfo) {
        Map<HttpMethodType, ApiMethodInfo> map = methodUrlMap.computeIfAbsent(apiMethodInfo.getUrl(), k -> new HashMap<>());
        if (map.put(apiMethodInfo.getType(), apiMethodInfo) != null) {
            throw new RuntimeException(apiMethodInfo + "apiMethodInfo严重错误,重复的定义:url" + apiMethodInfo.getUrl() + ",type:" + apiMethodInfo.getType());
        }
        if (methodNameMap.put(apiMethodInfo.getName(), apiMethodInfo) != null) {
            throw new RuntimeException(apiMethodInfo + "apiMethodInfo严重错误,重复的函数名称" + apiMethodInfo.getName() + ",type:" + apiMethodInfo.getType());
        }
        apiMethodInfo.setIndex(methodInfos.size());
        methodInfos.add(apiMethodInfo);
    }

    public List<ApiMethodInfo> getMethodInfos() {
        return methodInfos;
    }
}
