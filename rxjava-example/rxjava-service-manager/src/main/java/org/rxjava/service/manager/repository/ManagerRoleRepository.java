package org.rxjava.service.manager.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.manager.entity.ManagerRole;
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
public interface ManagerRoleRepository extends ReactiveSortingRepository<ManagerRole, String>, SpecialManagerRoleRepository {
    Flux<ManagerRole> findByManagerId(String managerId);
}

interface SpecialManagerRoleRepository {

}

class SpecialManagerRoleRepositoryImpl implements SpecialManagerRoleRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<ManagerRole> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, ManagerRole.class);
    }
}