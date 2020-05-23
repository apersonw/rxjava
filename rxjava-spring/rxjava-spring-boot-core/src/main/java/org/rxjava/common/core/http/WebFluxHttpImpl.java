package org.rxjava.common.core.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.rxjava.common.core.utils.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author happy
 */
@Slf4j
public class WebFluxHttpImpl implements WebHttp {
    private ConcurrentMap<String, WebClient> webClientMap = new ConcurrentHashMap<>();
    private WebClient.Builder webClientBuilder;

    public WebFluxHttpImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Request request(String baseUrl) {
        WebClient webClient = this.webClientMap
                .computeIfAbsent(baseUrl, key -> WebClient.builder()
                        .baseUrl(baseUrl)
                        .exchangeStrategies(ExchangeStrategies.builder().codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(10 * 1024 * 1024)).build()
                        )
                        .build()
                );
        return new RequestImpl(webClient, "");
    }

    @Override
    public Request request(String baseUrl, String path) {
        WebClient webClient = this.webClientMap
                .computeIfAbsent(baseUrl, key -> webClientBuilder
                        .baseUrl(baseUrl)
                        .exchangeStrategies(ExchangeStrategies.builder().codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(10 * 1024 * 1024)).build()
                        )
                        .build()
                );
        return new RequestImpl(webClient, path);
    }

    @Override
    public Request request(String host, String port, String path) {
        String baseUrl = "http://" + host + ":" + port;
        WebClient webClient = this.webClientMap
                .computeIfAbsent(baseUrl, key -> webClientBuilder
                        .baseUrl(baseUrl)
                        .exchangeStrategies(ExchangeStrategies.builder().codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(10 * 1024 * 1024)).build()
                        )
                        .build()
                );
        return new RequestImpl(webClient, path);
    }

    @Override
    public Request inner(String service) {
        String baseUrl = "http://fenglin-service-" + service;
        return request(baseUrl, "inner");
    }

    @Override
    public Request merchant(String service) {
        String baseUrl = "http://fenglin-service-" + service;
        return request(baseUrl, "merchant");
    }

    @Override
    public Request user(String service) {
        String baseUrl = "http://fenglin-service-" + service;
        return request(baseUrl, "");
    }

    private static class RequestImpl implements Request {
        private String path = "";
        private WebClient webClient;
        private MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        private MultiValueMap<String, String> cookies = new LinkedMultiValueMap<>();
        private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        private MultiValueMap<String, String> formMap = new LinkedMultiValueMap<>();
        private Object body;

        public RequestImpl(WebClient webClient, String path) {
            this.webClient = webClient;
            this.path = path;
        }

        public RequestImpl(WebClient webClient) {
            this.webClient = webClient;
        }

        @Override
        public Request queryParam(String name, Object... values) {
            for (Object value : values) {
                if (value == null) continue;
                params.add(name, value.toString());
            }
            return this;
        }

        @Override
        public Request body(Object json) {
            body = json;
            return this;
        }

        @Override
        public Request formBody(Map<String, String> params) {
            params.forEach((k, v) -> formMap.add(k, v));
            return this;
        }

        @Override
        public Request path(String path) {
            if (StringUtils.isEmpty(path)) {
                if (path.startsWith("/")) {
                    path = path.replaceFirst("/", "");
                }
                this.path = path;
            } else {
                if (path.startsWith("/")) {
                    this.path = this.path + path;
                } else {
                    this.path = this.path + "/" + path;
                }
            }
            return this;
        }

        @Override
        public Request header(String name, String value) {
            headers.add(name, value);
            return this;
        }

        @Override
        public Request cookie(String name, String value) {
            cookies.add(name, value);
            return this;
        }

        @Override
        public Mono<JsonNode> getDataObject() {
            return webClient
                    .get()
                    .uri(getUriBuilderURIFunction())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(s -> JsonUtils.deserialize(s, JsonNode.class));
        }

        @Override
        public Mono<JsonNode> postDataObject() {
            return requestBodySpec(HttpMethod.POST)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(s -> JsonUtils.deserialize(s, JsonNode.class));
        }

        @NotNull
        private WebClient.RequestBodySpec requestBodySpec(HttpMethod method) {
            WebClient.RequestBodySpec request;
            if (HttpMethod.POST == method) {
                request = webClient.post().uri(getUriBuilderURIFunction());
            } else if (HttpMethod.PATCH == method) {
                request = webClient.patch().uri(getUriBuilderURIFunction());
            } else {
                request = webClient.put().uri(getUriBuilderURIFunction());
            }
            if (!ObjectUtils.isEmpty(body)) {
                if (body instanceof CharSequence) {//可能传加密json字符串
                    request.contentType(MediaType.TEXT_PLAIN);
                    request.bodyValue(body);
                } else {
                    request.contentType(MediaType.APPLICATION_JSON);
                    request.bodyValue(JsonUtils.serialize(body));
                }
            } else if (formMap.size() > 0) {
                request.contentType(MediaType.APPLICATION_FORM_URLENCODED);
                request.bodyValue(formMap);
            }
            request.accept(MediaType.APPLICATION_JSON);
            request.headers(httpHeaders -> httpHeaders.addAll(headers));
            request.cookies(c -> c.addAll(cookies));
            return request;
        }

        @Override
        public Mono<JsonNode> post() {
            return requestBodySpec(HttpMethod.POST)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(getStringDataFunction());
//            return webClient
//                    .post()
//                    .uri(getUriBuilderURIFunction())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .map(getStringDataFunction());
        }


        @Override
        public Mono<JsonNode> put() {
            return requestBodySpec(HttpMethod.PUT)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(getStringDataFunction());
//            return webClient
//                    .put()
//                    .uri(getUriBuilderURIFunction())
//                    .accept(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .map(getStringDataFunction());
        }

        @Override
        public Mono<JsonNode> patch() {
            return requestBodySpec(HttpMethod.PATCH)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(getStringDataFunction());
//            return webClient
//                    .patch()
//                    .uri(getUriBuilderURIFunction())
//                    .accept(MediaType.APPLICATION_JSON)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .map(getStringDataFunction());
        }


        @Override
        public Mono<JsonNode> delete() {
            return webClient
                    .delete()
                    .uri(getUriBuilderURIFunction())
                    .accept(MediaType.APPLICATION_JSON)
                    .cookies(c -> c.addAll(cookies))
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(getStringDataFunction());
        }

        @Override
        public Mono<JsonNode> get() {
            return webClient
                    .get()
                    .uri(getUriBuilderURIFunction())
                    .accept(MediaType.APPLICATION_JSON)
                    .cookies(c -> c.addAll(cookies))
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(getStringDataFunction());
        }

        @NotNull
        private Function<UriBuilder, URI> getUriBuilderURIFunction() {
            return r -> {
                URI build = r.path(path)
                        .queryParams(params)
                        .build();
                log.info("发起请求:{}", build);
                return build;
            };
        }

        @NotNull
        private Function<String, JsonNode> getStringDataFunction() {
            return str -> {
                log.info("getStringDataFunction：{}", str);
                // OpResult result = JsonUtils.deserialize(str, OpResult.class);
                // if (result.getCode() != 0) {
                //     throw OpResultException.of(result.getCode(), result.getMsg());
                // }
                // if (StringUtils.isEmpty(result.getData())) {
                //     return new TextNode("");
                // }
                // return JsonUtils.deserialize(JsonUtils.serialize(result.getData()), JsonNode.class);
                return new TextNode("fixed me");
            };
        }

    }
}
