package org.rxjava.service.manager.admin;

import org.rxjava.common.core.annotation.Login;
import org.rxjava.service.manager.entity.Permission;
import org.rxjava.service.manager.entity.Role;
import org.rxjava.service.manager.form.LoginByPhoneSmsForm;
import org.rxjava.service.manager.model.ManagerModel;
import org.rxjava.service.manager.service.ManagerService;
import org.rxjava.service.manager.service.PermissionService;
import org.rxjava.service.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("admin")
public class AdminManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Login(false)
    @PostMapping("login")
    public Mono<String> loginByPhoneSms(
            @Valid LoginByPhoneSmsForm form
    ) {
        return managerService
                .loginByPhoneSms(form.getPhone(), form.getSms());
    }

    @GetMapping("token/manager")
    public Mono<ManagerModel> getTokenManager(
            @RequestParam String managerId
    ) {
        return managerService.findModelById(managerId);
    }

    @GetMapping("token/roles")
    public Flux<Role> getTokenRoleList(
            @RequestParam String managerId
    ) {
        return roleService.findListByManagerId(managerId);
    }

    @GetMapping("token/permissions")
    public Flux<Permission> getTokenPermissionList(
            @RequestParam String managerId
    ) {
        return permissionService.findListByManagerId(managerId);
    }

    @GetMapping("permissions")
    public Flux<Permission> getPermissionList(
    ) {
        return permissionService.findAllList();
    }
}
