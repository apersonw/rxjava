package org.rxjava.service.example.inner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rxjava.common.core.entity.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author happy 2019-06-29 22:39
 */
@RestController
@RequestMapping("inner")
public class InnerLoginInfoController {
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("checkToken/{token}")
    public Mono<LoginInfo> checkToken(@PathVariable String token) {
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

    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.just("hello boy");
    }
}
