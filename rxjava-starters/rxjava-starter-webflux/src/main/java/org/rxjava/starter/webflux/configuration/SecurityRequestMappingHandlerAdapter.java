package org.rxjava.starter.webflux.configuration;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.rxjava.webflux.core.annotation.Login;
import org.rxjava.webflux.core.exception.UnauthorizedException;
import org.rxjava.webflux.core.info.LoginInfo;
import org.rxjava.webflux.core.info.UserInfo;
import org.rxjava.webflux.utils.JsonUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.rxjava.webflux.service.starter.boot.LoginInfoArgumentResolver.LOGIN_REQUEST_ATTRIBUTE;

/**
 * @author happy 2019-04-16 23:05
 * 请求映射适配器
 */
public class SecurityRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {
    private static final Logger log = LogManager.getLogger();
    private static final String LOGIN_INFO = "loginInfo";

    SecurityRequestMappingHandlerAdapter() {
        super();
    }

    @Override
    public boolean supports(@NotNull Object handler) {
        return super.supports(handler);
    }

    @NotNull
    @Override
    public Mono<HandlerResult> handle(ServerWebExchange exchange, @NotNull Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        ServerHttpRequest request = exchange.getRequest();
        PathContainer path = request.getPath().pathWithinApplication();
        String pathValue = path.value();

        //微服务彼此间接口不注入登陆信息(信息均需要明确传入)
        if (pathValue.startsWith("/inner/")) {
            return super.handle(exchange, handler);
        }

        //检查接口方法是否需要登陆
        Login login = handlerMethod.getMethodAnnotation(Login.class);
        if (login == null || login.value()) {

            String loginInfoJson = request.getHeaders().getFirst(LOGIN_INFO);
            LoginInfo loginInfo = parseLoginJson(loginInfoJson);

            if (loginInfo == null) {
                throw UnauthorizedException.of("unauthorized");
            }
            //请求参数注入登陆信息对象
            exchange.getAttributes().put(LOGIN_REQUEST_ATTRIBUTE, loginInfo);
        }
        return super.handle(exchange, handler);
    }

    /**
     * 解析网关注入的登陆信息
     */
    private LoginInfo parseLoginJson(String loginInfoJson) {
        log.info("parseLoginJson:{}", loginInfoJson);
        if (StringUtils.isEmpty(loginInfoJson)) {
            return null;
        }
        try {
            loginInfoJson = URLDecoder.decode(loginInfoJson, "utf8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return JsonUtils.deserialize(loginInfoJson, UserInfo.class);
    }
}
