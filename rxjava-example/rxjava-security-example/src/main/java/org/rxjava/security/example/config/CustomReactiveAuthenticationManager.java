package org.rxjava.security.example.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-23 18:32
 * 自定义认证管理器
 */
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final ReactiveUserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    CustomReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
        Assert.notNull(userDetailsService, "userDetailsService cannot be null");
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String username = authentication.getName();
        final String presentedPassword = (String) authentication.getCredentials();
        return this.userDetailsService.findByUsername(username)
                .filter(u -> this.passwordEncoder.matches(presentedPassword, u.getPassword()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("Invalid Credentials"))))
                .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()) );
    }
}
