package org.rxjava.service.manager.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.manager.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @author happy 2019-06-17 13:37
 */
@Repository
public interface RoleRepository extends ReactiveSortingRepository<Role, String>, SpecialRoleRepository {
}

interface SpecialRoleRepository {

}

class SpecialRoleRepositoryImpl implements SpecialRoleRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<Role> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, Role.class);
    }
}