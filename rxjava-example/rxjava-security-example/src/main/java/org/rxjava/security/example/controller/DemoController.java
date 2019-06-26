package org.rxjava.security.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.rxjava.common.core.exception.ErrorMessageException;
import org.rxjava.common.core.utils.UUIDUtils;
import org.rxjava.security.example.entity.LoginLog;
import org.rxjava.security.example.entity.ManagerAuth;
import org.rxjava.security.example.entity.Permission;
import org.rxjava.security.example.entity.SecurityUser;
import org.rxjava.security.example.form.LoginByPhoneSmsForm;
import org.rxjava.security.example.repository.LoginLogRepository;
import org.rxjava.security.example.repository.ManagerAuthRepository;
import org.rxjava.security.example.type.IdentityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author happy 2019-06-13 13:13
 */
@Api(value = "Swagger test Controller", description = "learn how to use swagger")
@RestController
public class DemoController {
    private static final int TOKEN_LENGTH = 32;
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ManagerAuthRepository managerAuthRepository;
    @Autowired
    private LoginLogRepository loginLogRepository;

    /**
     * 手机验证码登陆(若手机号不存在则创建手机类型账号)
     */
    @ApiOperation("手机验证码登陆接口")
    @PostMapping("loginByPhoneSms")
    public Mono<String> loginByPhoneSms(
            @Valid LoginByPhoneSmsForm form
    ) {
        return managerAuthRepository
                .findByIdentityTypeAndIdentifier(IdentityType.PHONE.name(), form.getPhone())
                .switchIfEmpty(Mono.just(new ManagerAuth()).flatMap(managerAuth -> {
                    managerAuth.setIdentityType(IdentityType.PHONE.name());
                    managerAuth.setIdentifier(form.getPhone());
                    return managerAuthRepository
                            .save(managerAuth);
                }))
                .flatMap(managerAuth -> {
                    String token = newToken();
                    String managerId = managerAuth.getManagerId();
                    SecurityUser securityUser = new SecurityUser(
                            managerId,
                            managerAuth.getId(),
                            managerAuth.getIdentifier(),
                            IdentityType.PHONE.name(),
                            new ArrayList<>()
                    );
                    String securityUserStr;
                    try {
                        securityUserStr = objectMapper.writeValueAsString(securityUser);
                    } catch (JsonProcessingException e) {
                        throw ErrorMessageException.of("登陆信息解析json错误");
                    }
                    return reactiveRedisTemplate
                            .opsForValue()
                            .set(token, securityUserStr, Duration.ofMinutes(120))
                            .map(b -> token)
                            .doOnSuccess(tk -> {
                                //保存登陆日志信息
                                LoginLog loginLog = new LoginLog();
                                loginLog.setIdentityType(managerAuth.getIdentityType());
                                loginLog.setIdentifier(form.getPhone());
                                loginLog.setManagerId(managerId);
                                loginLog.setToken(tk);
                                loginLogRepository.save(loginLog).subscribe();
                            });
                });
    }

    /**
     * 获取信息
     */
    @GetMapping("info")
    public Mono<String> getInfo() {
        return Mono.just(UUID.randomUUID().toString());
    }

    private String newToken() {
        String uuid = UUIDUtils.randomUUIDToBase64();
        return uuid + RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH - uuid.length());
    }

    /**
     * 拥有角色
     */
    @GetMapping("hasRole")
    @PreAuthorize("hasRole('USER')")
    public Mono<String> hasRole() {
        return Mono.just(UUID.randomUUID().toString());
    }

    /**
     * 拥有以下其中一个角色
     */
    @GetMapping("hasAnyRole")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Mono<String> hasAnyRole() {
        return Mono.just(UUID.randomUUID().toString());
    }

    /**
     * 拥有权限(方法不好用，是阻塞的，而且没法覆盖默认的)
     */
    @GetMapping("hasPermission")
    @PreAuthorize("hasPermission(#id,'edit')")
    public Mono<String> hasPermission(
            String id,
            Permission permission
    ) {
        return Mono.just(UUID.randomUUID().toString());
    }
}