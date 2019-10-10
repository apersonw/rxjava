package org.rxjava.common.core.utils;

import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019/10/5 11:54
 */
public class RequestUtils {

    public static Mono<String> get(String requestUrl, MultiValueMap<String, String> params) {
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

    public static String post(String requestUrl) {
        WebClient.create();
        return null;
    }

    public static void main(String[] args) {
        RequestUtils.get("https://www.baidu.com", new LinkedMultiValueMap<>());
    }
}