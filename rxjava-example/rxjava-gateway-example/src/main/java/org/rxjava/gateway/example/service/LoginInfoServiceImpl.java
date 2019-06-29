package org.rxjava.gateway.example.service;

import org.rxjava.api.example.inner.InnerLoginInfoApi;
import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.DefaultLoginInfoServiceImpl;
import org.rxjava.common.core.service.LoginInfoService;
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
    public Mono<LoginInfo> checkToken(String token) {
        return innerLoginInfoApi.checkToken(token).map(r->{
            LoginInfo loginInfo = new LoginInfo();
            BeanUtils.copyProperties(r,loginInfo);
            return loginInfo;
        });
    }

}
