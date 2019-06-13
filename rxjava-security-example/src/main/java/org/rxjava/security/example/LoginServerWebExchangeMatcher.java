package org.rxjava.security.example;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-12 01:42
 */
public class LoginServerWebExchangeMatcher implements ServerWebExchangeMatcher {
    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();
        PathContainer path = request.getPath().pathWithinApplication();
        String pathValue = path.value();

        Mono<MatchResult> match = MatchResult.match();
        //内部接口不鉴权
        if ("/login".equals(pathValue)||pathValue.startsWith("/inner/")) {
            return match;
        }

        //如果没有Token，则不鉴权
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isEmpty(token)) {
            return match;
        }

        return MatchResult.notMatch();
    }
}
