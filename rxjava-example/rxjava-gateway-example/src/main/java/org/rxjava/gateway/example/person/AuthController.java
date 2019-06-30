package org.rxjava.gateway.example.person;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

/**
 * @author happy 2019-06-29 16:18
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @GetMapping("login")
    public Mono<String> login() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserAuthId("userAuthId");
        loginInfo.setUserId("userId");
        loginInfo.setIdentityType("PHONE");

        String loginInfoJson;
        try {
            loginInfoJson = URLEncoder.encode(JsonUtils.serialize(loginInfo), "utf8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return reactiveRedisTemplate
                .opsForValue()
                .set("token", loginInfoJson, Duration.ofSeconds(30))
                .map(r -> "token");
    }
}
