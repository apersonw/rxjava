package top.rxjava.starter.webflux.configuration;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.rxjava.apikit.annotation.Login;
import top.rxjava.common.core.exception.ErrorMessageException;
import top.rxjava.common.core.exception.UnauthorizedException;
import top.rxjava.common.utils.JsonUtils;
import top.rxjava.common.core.info.TokenInfo;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static top.rxjava.starter.webflux.configuration.LoginInfoArgumentResolver.LOGIN_REQUEST_ATTRIBUTE;

/**
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

    @SneakyThrows
    @NotNull
    @Override
    public Mono<HandlerResult> handle(ServerWebExchange exchange, @NotNull Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        ServerHttpRequest request = exchange.getRequest();
        PathContainer path = request.getPath().pathWithinApplication();
        String pathValue = path.value();

        //文档路径不注入登录信息
        if (pathValue.startsWith("/v3/api-docs") || pathValue.startsWith("/swagger-ui.html")) {
            return super.handle(exchange, handler);
        }

        //微服务彼此间接口不注入登陆信息(信息均需要明确传入)
        if (pathValue.startsWith("/inner/")) {
            return super.handle(exchange, handler);
        }

        //检查接口方法是否需要登陆
        return Mono
                .fromCallable(() -> {
                    Login login = handlerMethod.getMethodAnnotation(Login.class);
                    if (login == null || login.value()) {
                        String loginInfoJson = request.getHeaders().getFirst(LOGIN_INFO);
                        TokenInfo tokenInfo = parseLoginJson(loginInfoJson);
                        if (tokenInfo == null) {
                            throw UnauthorizedException.of(HttpStatus.UNAUTHORIZED.getReasonPhrase());
                        }
                        //请求参数注入登陆信息对象
                        exchange.getAttributes().put(LOGIN_REQUEST_ATTRIBUTE, tokenInfo);
                    }
                    return "login";
                })
                .flatMap(l -> super.handle(exchange, handler));
    }

    /**
     * 解析网关注入的登陆信息
     */
    private TokenInfo parseLoginJson(String loginInfoJson) {
        if (StringUtils.isEmpty(loginInfoJson)) {
            return null;
        }
        try {
            loginInfoJson = URLDecoder.decode(loginInfoJson, String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw ErrorMessageException.of(e.getMessage());
        }
        TokenInfo tokenInfo = JsonUtils.deserialize(loginInfoJson, TokenInfo.class);
        log.info("用户登陆信息:{}", tokenInfo);
        if (ObjectUtils.isEmpty(tokenInfo.getUserId())) {
            throw ErrorMessageException.of("tokenUserIdIsNotEmpty");
        }
        return tokenInfo;
    }
}