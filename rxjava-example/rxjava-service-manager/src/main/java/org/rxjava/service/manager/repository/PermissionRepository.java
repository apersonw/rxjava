package org.rxjava.service.manager.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.manager.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * @author happy 2019-06-17 13:37
 */
@Repository
public interface PermissionRepository extends ReactiveSortingRepository<Permission, String>, SpecialPermissionRepository {
    Mono<Permission> findByPathAndMethod(String path, String method);
}

interface SpecialPermissionRepository {

}

class SpecialPermissionRepositoryImpl implements SpecialPermissionRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<Permission> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, Permission.class);
    }
}