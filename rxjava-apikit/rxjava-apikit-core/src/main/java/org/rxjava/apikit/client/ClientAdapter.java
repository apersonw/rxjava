package org.rxjava.apikit.client;

import reactor.core.publisher.Mono;

import java.lang.reflect.Type;

/**
 * @author happy
 * Api适配器
 */
public interface ClientAdapter {
    /**
     * 发起http请求
     *
     * @param method     请求方法{@link org.rxjava.apikit.core.HttpMethodType}
     * @param uri        请求的Uri
     * @param inputParam 入参
     * @param returnType 返回值类型
     */
    <T> Mono<T> request(String method, String uri, InputParam inputParam, Type returnType);
}