package org.rxjava.service.manager.service;

import org.rxjava.service.manager.entity.Role;
import org.rxjava.service.manager.repository.ManagerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RoleService {
    @Autowired
    private ManagerRoleRepository managerRoleRepository;

    public Flux<Role> findListByManagerId(String managerId) {
        return managerRoleRepository
                .findByManagerId(managerId)
                .map(managerRole -> new Role());
    }
}
