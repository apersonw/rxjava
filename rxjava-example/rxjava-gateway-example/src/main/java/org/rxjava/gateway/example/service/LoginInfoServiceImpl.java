package org.rxjava.gateway.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author happy 2019-06-29 18:36
 */
@Service
public class LoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 1、获取loginInfo
     * 2、检查permission
     * 3、返回loginInfo
     */
    @Override
    public Mono<LoginInfo> checkPermission(String token, String requestPath, String methodValue) {
        return reactiveRedisTemplate
                .opsForValue()
                .get(token)
                .map(loginInfoStr -> {
                    LoginInfo loginInfo = null;
                    try {
                        String decode = URLDecoder.decode(loginInfoStr, "utf-8");
                        loginInfo = objectMapper.readValue(decode, LoginInfo.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return loginInfo;
                })
                .switchIfEmpty(Mono.just(new LoginInfo())
                        .map(r -> {
                            LoginInfo loginInfo = new LoginInfo();
                            loginInfo.setUserAuthId("userAuthId");
                            loginInfo.setUserId("userId");
                            loginInfo.setIdentityType("PHONE");
                            loginInfo.setForbidden(true);
                            return loginInfo;
                        })
                );
    }
}
