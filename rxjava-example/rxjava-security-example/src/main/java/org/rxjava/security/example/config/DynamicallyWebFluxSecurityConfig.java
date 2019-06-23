//package org.rxjava.security.example.config;
//
//import org.rxjava.security.example.repository.DynamicallySecurityContextRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import reactor.core.publisher.Mono;
//
///**
// * @author happy 2019-06-13 13:11
// */
//@EnableReactiveMethodSecurity
//@EnableWebFluxSecurity
//public class DynamicallyWebFluxSecurityConfig {
//    @Autowired
//    private DynamicallySecurityContextRepository dynamicallySecurityContextRepository;
//
//    @Bean
//    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .exceptionHandling()
//                .authenticationEntryPoint((swe, e) -> Mono
//                        .fromRunnable(() -> swe
//                                .getResponse()
//                                .setStatusCode(HttpStatus.UNAUTHORIZED)
//                        )
//                )
//                .accessDeniedHandler((swe, e) -> Mono
//                        .fromRunnable(() -> swe
//                                .getResponse()
//                                .setStatusCode(HttpStatus.FORBIDDEN)
//                        ))
//                .and()
//                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                //设置安全上下文持久化仓库
//                .securityContextRepository(dynamicallySecurityContextRepository)
//                //配置授权匹配
//                .authorizeExchange()
//                //OPTIONS请求均通过
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                .pathMatchers("/login").permitAll()
//                .pathMatchers("/actuator/**").permitAll()
//                //其他所有路径均需要授权
//                .anyExchange().authenticated()
//                .and()
//                .build();
//    }
//}
