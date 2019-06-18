package org.rxjava.service.example.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.example.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @author happy 2019-06-17 13:37
 */
@Repository
public interface ExampleRepository extends ReactiveSortingRepository<Example, String>, SpecialExampleRepository {
}

interface SpecialExampleRepository {

}

class SpecialExampleRepositoryImpl implements SpecialExampleRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<Example> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, Example.class);
    }
}