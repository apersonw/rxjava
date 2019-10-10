package org.rxjava.common.core.tools;

import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019/10/5 11:54
 */
public class RequestTool {

    public Mono<String> get(String requestUrl) {
        return get(requestUrl, new LinkedMultiValueMap<>());
    }

    /**
     * get请求
     * 路径参数
     */
    public Mono<String> get(String requestUrl, MultiValueMap<String, String> params) {
        return WebClient.create(requestUrl)
                .method(HttpMethod.GET)
                .uri(uriBuilder -> {
                    if (params.isEmpty()) {
                        return uriBuilder.build();
                    }
                    return uriBuilder.queryParams(params).build();
                })
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> post(String requestUrl) {
        return get(requestUrl, new LinkedMultiValueMap<>());
    }

    /**
     * post请求
     * formdata参数
     */
    public Mono<String> post(String requestUrl, MultiValueMap<String, String> params) {
        return WebClient.create(requestUrl)
                .method(HttpMethod.POST)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class);
    }

    public static void main(String[] args) {
        new RequestTool().get("https://www.baidu.com").block();
    }
}