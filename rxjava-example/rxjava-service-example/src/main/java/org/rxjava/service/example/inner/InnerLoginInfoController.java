package org.rxjava.service.example.inner;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.common.core.service.LoginInfoService;
import org.rxjava.service.example.form.CheckPermissionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author happy 2019-06-29 22:39
 */
@RestController
@RequestMapping("inner")
public class InnerLoginInfoController {
    @Autowired
    private LoginInfoService loginInfoService;

    @GetMapping("checkToken/{token}")
    public Mono<LoginInfo> checkToken(@PathVariable String token, @PathVariable String loginType) {
        return loginInfoService.checkToken(token, loginType);
    }

    @GetMapping("checkPermission")
    public Mono<Boolean> checkPermission(@Valid CheckPermissionForm form) {
        return loginInfoService.checkPermission(form.getUserAuthId(), form.getPath(), form.getMethod());
    }

    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.just("hello boy");
    }
}
