package top.rxjava.starter.webflux.configuration;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.rxjava.common.core.info.TokenInfo;

import static top.rxjava.starter.webflux.configuration.SecurityRequestMappingHandlerAdapter.LOGIN_INFO;

/**
 * @author happy
 */
public class ReactiveTokenInfoFilter implements WebFilter {
    private static final String TOKEN_USERID_INFO = "tokenUserId";
    @NotNull
    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).contextWrite(context -> {
            String loginInfoJson = exchange.getRequest().getHeaders().getFirst(LOGIN_INFO);
            if (StringUtils.isNotBlank(loginInfoJson)) {
                TokenInfo tokenInfo = SecurityRequestMappingHandlerAdapter.parseLoginJson(loginInfoJson);
                assert tokenInfo != null;
                return context.put(TOKEN_USERID_INFO, tokenInfo.getUserId());
            }
            return context;
        });
    }
}
