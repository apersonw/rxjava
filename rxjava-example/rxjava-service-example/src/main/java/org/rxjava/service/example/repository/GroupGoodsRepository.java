package org.rxjava.service.example.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.example.entity.GroupGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

/**
 * @author happy 2019-06-17 13:37
 */
@Repository
public interface GroupGoodsRepository extends ReactiveSortingRepository<GroupGoods, String>, SpecialGroupGoodsRepository {
}

interface SpecialGroupGoodsRepository {
    Flux<GroupGoods> findList(String groupId, Pageable pageable);
}

class SpecialGroupGoodsRepositoryImpl implements SpecialGroupGoodsRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<GroupGoods> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, GroupGoods.class);
    }

    @Override
    public Flux<GroupGoods> findList(String groupId, Pageable pageable) {
        return Flux.empty();
    }
}