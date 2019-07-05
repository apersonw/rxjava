package org.rxjava.gateway.starter.config;

import org.apache.commons.lang3.StringUtils;
import org.rxjava.common.core.exception.LoginRuntimeException;
import org.rxjava.common.core.service.DefaultLoginInfoServiceImpl;
import org.rxjava.common.core.service.LoginInfoService;
import org.rxjava.common.core.type.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author happy 2019-06-29 03:30
 */
@EnableWebFluxSecurity
@Import({CheckTokenConfig.class})
public class RxJavaWebFluxSecurityConfig {
    @Autowired
    private LoginInfoService loginInfoService;

    /**
     * 默认校验均不通过，客户端需要自行实现
     */
    @Bean
    @ConditionalOnMissingBean
    public LoginInfoService loginInfoService() {
        return new DefaultLoginInfoServiceImpl();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return Mono::just;
    }

    /**
     * token授权过滤器
     */
    @Bean
    public AuthenticationWebFilter authenticationFilter(ReactiveAuthenticationManager reactiveAuthenticationManager) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(reactiveAuthenticationManager);
        filter.setServerAuthenticationConverter(this::authenticationConverter);
        filter.setAuthenticationFailureHandler((exchange, exception) -> LoginRuntimeException.mono("401 unauthorized"));
        filter.setAuthenticationSuccessHandler(new CustomServerAuthenticationSuccessHandler());
        return filter;
    }

    /**
     * Token换LoginInfo
     */
    private Mono<Authentication> authenticationConverter(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isEmpty(authorization)) {
            return Mono.empty();
        }

        String loginType = LoginType.PERSON.name();
        String pathValue = request.getPath().value();

        //检查路径是否走管理
        Pattern adminPattern = compile("/admin/");
        boolean adminFind = adminPattern.matcher(pathValue).find();
        if (adminFind) {
            loginType = LoginType.ADMIN.name();
        }

        if (!adminFind) {
            //检查路径是否走第三方
            Pattern thirdPattern = compile("/third/");
            boolean thirdFind = thirdPattern.matcher(pathValue).find();
            if (thirdFind) {
                loginType = LoginType.THIRD.name();
            }
        }

        return loginInfoService
                .checkToken(authorization, loginType)
                .map(loginInfo -> new AuthenticationToken(authorization));
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationWebFilter authenticationFilter) {
        return http
                .authorizeExchange()
                .pathMatchers("/**/inner/**").denyAll()
                .pathMatchers("/inner/**").denyAll()
                .pathMatchers("/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling()
                .authenticationEntryPoint((exchange, e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((exchange, e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                //取消csrf
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}