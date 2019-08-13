package org.rxjava.service.manager.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.manager.entity.Manager;
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
public interface ManagerRepository extends ReactiveSortingRepository<Manager, String>, SpecialManagerRepository {
}

interface SpecialManagerRepository {

}

class SpecialManagerRepositoryImpl implements SpecialManagerRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<Manager> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, Manager.class);
    }
}