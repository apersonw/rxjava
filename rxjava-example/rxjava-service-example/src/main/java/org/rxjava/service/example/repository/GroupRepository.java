package org.rxjava.service.example.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.example.entity.Goods;
import org.rxjava.service.example.entity.Group;
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
public interface GroupRepository extends ReactiveSortingRepository<Group, String>, SpecialGroupRepository {
    Flux<Group> findAllByGroupCategoryId(String groupCategoryId);
}

interface SpecialGroupRepository {

}

class SpecialGroupRepositoryImpl implements SpecialGroupRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<Group> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, Group.class);
    }
}