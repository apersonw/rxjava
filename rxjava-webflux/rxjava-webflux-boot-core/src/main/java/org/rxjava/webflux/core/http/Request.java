package org.rxjava.webflux.core.http;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author happy
 */
public interface Request {

    Request queryParam(String name, Object... values);

    Request body(Object json);

    Request formBody(Map<String, String> params);

    Request path(String path);

    default Request queryParams(Map<String, String> params) {
        params.forEach(this::queryParam);
        return this;
    }

    Request header(String name, String value);

    Request cookie(String name, String value);

    /**
     * 不解包，返回原始json
     */
    Mono<JsonNode> getDataObject();

    /**
     * 不解包，返回原始json
     */
    Mono<JsonNode> postDataObject();

    Mono<JsonNode> post();

    Mono<JsonNode> put();

    Mono<JsonNode> patch();

    Mono<JsonNode> delete();

    Mono<JsonNode> get();

}
