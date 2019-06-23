package org.rxjava.security.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.exception.ErrorMessageException;
import org.rxjava.common.core.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

/**
 * @author happy 2019-06-13 13:13
 */
@RestController
public class DemoController {
    private static final int TOKEN_LENGTH = 32;
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 登陆
     */
    @PostMapping("login")
    public Mono<String> login() {
        String token = newToken();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId("userId");
        loginInfo.setIdentityType("phone");
        loginInfo.setUserAuthId("userAuthId");
        String loginInfoStr;
        try {
            loginInfoStr = objectMapper.writeValueAsString(loginInfo);
        } catch (JsonProcessingException e) {
            throw ErrorMessageException.of("登陆信息解析json错误");
        }
        return reactiveRedisTemplate
                .opsForValue()
                .set(token, loginInfoStr, Duration.ofMinutes(120))
                .map(b -> token);
    }

    /**
     * 获取信息
     */
    @GetMapping("info")
    public Mono<String> getInfo() {
        return Mono.just(UUID.randomUUID().toString());
    }

    /**
     * 用户权限测试
     */
    @GetMapping("userEditTest")
    @PreAuthorize("hasPermission(#user, 'edit')")
    public Mono<String> userEditTest() {
        return Mono.just(UUID.randomUUID().toString());
    }

    private String newToken() {
        String uuid = UUIDUtils.randomUUIDToBase64();
        return uuid + RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH - uuid.length());
    }
}