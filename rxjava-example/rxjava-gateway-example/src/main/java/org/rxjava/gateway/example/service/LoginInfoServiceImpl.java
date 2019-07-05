package org.rxjava.gateway.example.service;

import org.rxjava.api.example.inner.InnerLoginInfoApi;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.DefaultLoginInfoServiceImpl;
import org.rxjava.common.core.service.LoginInfoService;
import org.rxjava.common.core.type.LoginType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-06-29 18:36
 */
@Service
public class LoginInfoServiceImpl extends DefaultLoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    private InnerLoginInfoApi innerLoginInfoApi;

    /**
     * Token校验
     */
    @Override
    public Mono<LoginInfo> checkToken(String token, String loginType) {
        switch (loginType) {
            case "PERSON":
                return innerLoginInfoApi.checkToken(token,loginType).map(r -> {
                    LoginInfo loginInfo = new LoginInfo();
                    BeanUtils.copyProperties(r, loginInfo);
                    loginInfo.setLoginType(loginType);
                    return loginInfo;
                });
            //todo:待写token校验逻辑
            case "ADMIN":
            case "THIRD":
            default:
                return Mono.empty();
        }
    }

}
