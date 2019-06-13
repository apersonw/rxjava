package org.rxjava.security.example;

import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-12 09:56
 */
public class SecurityRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    SecurityRequestMappingHandlerAdapter() {
        super();
    }

    @Override
    public boolean supports(Object handler) {
        return super.supports(handler);
    }

    @Override
    public Mono<HandlerResult> handle(ServerWebExchange exchange, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        ServerHttpRequest request = exchange.getRequest();
        PathContainer path = request.getPath().pathWithinApplication();
        String pathValue = path.value();
        //内部接口不做登陆检查
        if (pathValue.startsWith("/inner")) {
            return super.handle(exchange, handler);
        }

        return super.handle(exchange, handler);
    }
}
