package org.rxjava.apikit.client;

import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author happy
 * 客户端适配器
 */
public interface ClientAdapter {
    /**
     * 发起http请求
     *
     * @param method 请求方法{@link org.rxjava.apikit.core.HttpMethodType}
     * @param uri    请求的Uri
     * @param form   Post请求表单数据
     * @param returnType 返回值类型
     */
    <T> Mono<T> request(String method, String uri, List<Entry<String, Object>> form, Type returnType);
}