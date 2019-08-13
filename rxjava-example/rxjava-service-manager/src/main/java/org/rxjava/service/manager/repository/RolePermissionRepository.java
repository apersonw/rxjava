package org.rxjava.service.manager.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.manager.entity.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

/**
 * @author happy 2019-06-17 13:37
 */
@Repository
public interface RolePermissionRepository extends ReactiveSortingRepository<RolePermission, String>, SpecialRolePermissionRepository {
    Flux<RolePermission> findByRoleId(String roleId);
}

interface SpecialRolePermissionRepository {

}

class SpecialRolePermissionRepositoryImpl implements SpecialRolePermissionRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<RolePermission> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, RolePermission.class);
    }
}