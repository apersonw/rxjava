package org.rxjava.service.manager.admin;

import org.rxjava.common.core.annotation.Login;
import org.rxjava.service.manager.entity.Permission;
import org.rxjava.service.manager.entity.Role;
import org.rxjava.service.manager.model.ManagerModel;
import org.rxjava.service.manager.service.ManagerService;
import org.rxjava.service.manager.service.PermissionService;
import org.rxjava.service.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    @GetMapping("token/manager")
    public Mono<ManagerModel> getTokenManager(
            @RequestParam String managerId
    ) {
        return managerService.findModelById(managerId);
    }

    @Login(false)
    @GetMapping("token/roles")
    public Flux<Role> getTokenRoleList(
            @RequestParam String managerId
    ) {
        return roleService.findListByManagerId(managerId);
    }

    @Login(false)
    @GetMapping("token/permissions")
    public Flux<Permission> getTokenPermissionList(
            @RequestParam String managerId
    ) {
        return permissionService.findListByManagerId(managerId);
    }

    @Login(false)
    @GetMapping("permissions")
    public Flux<Permission> getPermissionList(
    ) {
        return permissionService.findAllList();
    }
}
