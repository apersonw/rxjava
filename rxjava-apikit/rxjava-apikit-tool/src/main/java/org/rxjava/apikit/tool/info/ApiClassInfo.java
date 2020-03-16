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
public class ApiClassInfo extends ClassInfo {
    /**
     * url作为key的map
     */
    private Map<String, Map<HttpMethodType, ApiMethodClassInfo>> methodUrlMap = new HashMap<>();
    /**
     * 方法名methodName作为key的map
     */
    private Map<String, ApiMethodClassInfo> methodNameMap = new HashMap<>();
    /**
     * api方法列表
     */
    private List<ApiMethodClassInfo> methodInfos = new ArrayList<>();
    /**
     * 添加api方法信息
     */
    public void addApiMethod(ApiMethodClassInfo apiMethodClassInfo) {
        Map<HttpMethodType, ApiMethodClassInfo> map = methodUrlMap.computeIfAbsent(apiMethodClassInfo.getUrl(), k -> new HashMap<>());
        if (map.put(apiMethodClassInfo.getType(), apiMethodClassInfo) != null) {
            throw new RuntimeException(apiMethodClassInfo + "apiMethodInfo严重错误,重复的定义:url" + apiMethodClassInfo.getUrl() + ",type:" + apiMethodClassInfo.getType());
        }
        if (methodNameMap.put(apiMethodClassInfo.getName(), apiMethodClassInfo) != null) {
            throw new RuntimeException(apiMethodClassInfo + "apiMethodInfo严重错误,重复的函数名称" + apiMethodClassInfo.getName() + ",type:" + apiMethodClassInfo.getType());
        }
        apiMethodClassInfo.setIndex(methodInfos.size());
        methodInfos.add(apiMethodClassInfo);
    }

    public List<ApiMethodClassInfo> getMethodInfos() {
        return methodInfos;
    }
}
