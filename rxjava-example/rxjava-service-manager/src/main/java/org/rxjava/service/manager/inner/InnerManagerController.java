package org.rxjava.service.manager.inner;

import org.rxjava.service.manager.form.AuthenticationForm;
import org.rxjava.service.manager.model.ManagerModel;
import org.rxjava.service.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("inner")
public class InnerManagerController {
    @Autowired
    private ManagerService managerService;

    /**
     * 鉴权
     */
    @GetMapping("authentication")
    public Mono<ManagerModel> authentication(
            @Valid AuthenticationForm form
            ) {
        return Mono.just(new ManagerModel());
    }
}
