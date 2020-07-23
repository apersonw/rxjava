package org.rxjava.service.example.repository;

import org.rxjava.common.core.exception.ErrorMessageException;
import org.rxjava.service.example.entity.ExampleMysql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface ExampleMysqlRepository extends R2dbcRepository<ExampleMysql, Long>, SpecialExampleMysqlRepository {
}

interface SpecialExampleMysqlRepository {
    Mono<ExampleMysql> findOneById();
}

class SpecialExampleMysqlRepositoryImpl implements SpecialExampleMysqlRepository {

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Mono<ExampleMysql> findOneById() {
        return databaseClient.select().from(ExampleMysql.class).fetch().one().switchIfEmpty(ErrorMessageException.mono("error"));
    }
}