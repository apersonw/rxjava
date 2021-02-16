package org.rxjava.webflux.core.http;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author happy
 */
public interface WebHttp {

    /**
     * 没有走内部负载均衡,网络请求。主要用作请求外网接口
     */
    Request request(String baseUrl);

    /**
     * 内部调用
     *
     * @param baseUrl 服务地址
     * @param path    网关路径 如inner/merchant 客户端不填
     * @return
     */
    Request request(String baseUrl, String path);

    Request request(String host, String port, String path);

    /**
     * 内部服务请求
     * 如请求订单服务仅需填写order
     */
    Request inner(String service);

    Request merchant(String service);

    Request user(String service);

    class Builder {
        public static WebHttp buildWebFlux(WebClient.Builder webClientBuilder) {
            return new WebFluxHttpImpl(webClientBuilder);
        }
    }
}
