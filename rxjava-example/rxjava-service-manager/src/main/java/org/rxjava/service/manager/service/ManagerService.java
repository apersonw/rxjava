package org.rxjava.service.manager.service;

import org.rxjava.service.manager.entity.Manager;
import org.rxjava.service.manager.entity.RolePermission;
import org.rxjava.service.manager.model.ManagerModel;
import org.rxjava.service.manager.repository.ManagerRepository;
import org.rxjava.service.manager.repository.ManagerRoleRepository;
import org.rxjava.service.manager.repository.PermissionRepository;
import org.rxjava.service.manager.repository.RolePermissionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ManagerRoleRepository managerRoleRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public Mono<Manager> findById(String managerId) {
        return Mono.just(new Manager());
    }

    public Mono<ManagerModel> findModelById(String managerId) {
        return findById(managerId).map(this::managerToModel);
    }

    private ManagerModel managerToModel(Manager manager) {
        ManagerModel managerModel = new ManagerModel();
        BeanUtils.copyProperties(manager, managerModel);
        return managerModel;
    }

    /**
     * token换取managerModel
     * 1、检查路径是否需要鉴权(不需鉴权，则直接为真)
     * 2、检查用户是否拥有权限
     */
    public Mono<ManagerModel> tokenToManagerModel(String token, String path, String method) {
        String managerId = "testId";

        return permissionRepository
                .findByPathAndMethod(path, method)
                .flatMap(permission -> managerRoleRepository
                        .findByManagerId(managerId)
                        .flatMap(managerRole -> rolePermissionRepository
                                .findByRoleId(managerRole.getRoleId())
                        )
                        .distinct(RolePermission::getPermissionId)
                        .filter(rolePermission -> permission.getId().equals(rolePermission.getPermissionId()))
                        .singleOrEmpty()
                        .map(p -> true)
                        .switchIfEmpty(Mono.just(false))
                )
                .switchIfEmpty(Mono.just(true))
                .filter(b -> b)
                .flatMap(b -> managerRepository.findById(managerId).map(this::managerToModel));
    }
}