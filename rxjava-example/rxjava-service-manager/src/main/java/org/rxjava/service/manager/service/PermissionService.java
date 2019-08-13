package org.rxjava.service.manager.service;

import org.rxjava.service.manager.entity.ManagerRole;
import org.rxjava.service.manager.entity.Permission;
import org.rxjava.service.manager.entity.RolePermission;
import org.rxjava.service.manager.repository.ManagerRoleRepository;
import org.rxjava.service.manager.repository.PermissionRepository;
import org.rxjava.service.manager.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PermissionService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private ManagerRoleRepository managerRoleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public Flux<Permission> findListByManagerId(String managerId) {
        return managerRoleRepository
                .findByManagerId(managerId)
                .distinct(ManagerRole::getRoleId)
                .flatMap(managerRole -> rolePermissionRepository
                        .findByRoleId(managerRole.getRoleId())
                        .distinct(RolePermission::getPermissionId)
                        .map(rolePermission -> new Permission())
                );
    }

    public Flux<Permission> findAllList() {
        return permissionRepository.findAll();
    }
}
