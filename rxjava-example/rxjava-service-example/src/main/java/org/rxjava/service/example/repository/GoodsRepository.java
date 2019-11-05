package org.rxjava.service.example.repository;

import org.rxjava.common.core.mongo.PageAgent;
import org.rxjava.service.example.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @author happy 2019-06-17 13:37
 */
@Repository
public interface GoodsRepository extends ReactiveSortingRepository<Goods, String>, SpecialGoodsRepository {
}

interface SpecialGoodsRepository {

}

class SpecialGoodsRepositoryImpl implements SpecialGoodsRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private PageAgent<Goods> pageAgent;

    @PostConstruct
    private void init() {
        pageAgent = new PageAgent<>(reactiveMongoTemplate, Goods.class);
    }
}