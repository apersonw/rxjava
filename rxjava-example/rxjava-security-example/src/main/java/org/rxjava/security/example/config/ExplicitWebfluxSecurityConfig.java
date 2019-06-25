package org.rxjava.security.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.exception.LoginRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author happy 2019-06-23 15:45
 * 明确的权限配置
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ExplicitWebfluxSecurityConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Autowired
    private ReactiveUserDetailsService reactiveUserDetailsService;

    /**
     * token授权过滤器
     */
    private AuthenticationWebFilter tokenAuthenticationFilter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(tokenAuthenticationManager());
        filter.setServerAuthenticationConverter(tokenAuthenticationConverter()::apply);
        filter.setAuthenticationFailureHandler((exchange, exception) -> Mono.error(exception));
        return filter;
    }

    /**
     * 将token转换为JwtAuthenticationToken
     */
    private Function<ServerWebExchange, Mono<Authentication>> tokenAuthenticationConverter() {
        return serverWebExchange -> {
            String authorization = serverWebExchange.getRequest()
                    .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isEmpty(authorization)) {
                return Mono.empty();
            }
            return Mono.just(new JwtAuthenticationToken(authorization));
        };
    }

    /**
     * 从redis中获取loginInfo转换为JwtAuthenticationToken
     */
    private ReactiveAuthenticationManager tokenAuthenticationManager() {
        return authentication -> {
            String token = (String) authentication.getCredentials();
            return reactiveRedisTemplate.opsForValue()
                    .get(token)
                    .switchIfEmpty(Mono.defer(() -> Mono.error(LoginRuntimeException.of("未找到token"))))
                    .map(loginInfoStr -> {
                        LoginInfo loginInfo = null;
                        try {
                            loginInfo = objectMapper.readValue(loginInfoStr, LoginInfo.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return loginInfo;
                    })
                    .flatMap(loginInfo -> reactiveUserDetailsService.findByUsername(loginInfo.getUserId())
                            .map(userDetails -> new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities()))
                    );
        };
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling()
                .authenticationEntryPoint((exchange, e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((exchange, e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                //禁用缓存
                .headers().cache().disable().and()
                //取消csrf
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .addFilterAt(tokenAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
