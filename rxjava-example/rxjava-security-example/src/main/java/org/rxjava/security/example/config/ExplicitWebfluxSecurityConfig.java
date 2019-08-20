package org.rxjava.security.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.common.core.exception.LoginInfoException;
import org.rxjava.security.example.entity.SecurityUser;
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
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author happy 2019-06-23 15:45
 * 明确的权限配置
 */
@EnableSwagger2WebFlux
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ExplicitWebfluxSecurityConfig implements WebFluxConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

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
     * 将token转换为JwtAuthenticationToken对象，实际上里面只存了token
     */
    private Function<ServerWebExchange, Mono<Authentication>> tokenAuthenticationConverter() {
        return serverWebExchange -> {
            String authorization = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
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
                    .switchIfEmpty(Mono.defer(() -> Mono.error(LoginInfoException.of("未找到token"))))
                    .map(securityUserStr -> {
                        SecurityUser securityUser = null;
                        try {
                            securityUser = objectMapper.readValue(securityUserStr, SecurityUser.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return securityUser;
                    })
                    .map(securityUser -> new JwtAuthenticationToken(
                                    securityUser,
                                    token,
                                    securityUser.getAuthorities()
                            )
                    );
        };
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/loginByPhoneSms").permitAll()
                .pathMatchers("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**").permitAll()
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

    /**********************Api文档*************************/
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.rxjava"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger构建RESTful API")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
