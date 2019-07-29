package org.rxjava.service.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.DefaultLoginInfoServiceImpl;
import org.rxjava.common.core.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author happy 2019-06-30 00:52
 */
@Service
public class LoginInfoServiceImpl extends DefaultLoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<LoginInfo> checkToken(String token, String loginType) {
        return reactiveRedisTemplate
                .opsForValue()
                .get(token)
                .map(loginInfoStr -> {
                    LoginInfo loginInfo = null;
                    try {
                        String decode = URLDecoder.decode(loginInfoStr, "utf8");
                        loginInfo = objectMapper.readValue(decode, LoginInfo.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return loginInfo;
                });
    }

    @Override
    public Mono<Boolean> checkPermission(String userAuthId, String path, String method) {
        return Mono.just(true);
    }
}
