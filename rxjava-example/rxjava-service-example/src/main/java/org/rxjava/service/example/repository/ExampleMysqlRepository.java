package org.rxjava.service.example.repository;

import org.rxjava.service.example.entity.ExampleMysql;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleMysqlRepository extends R2dbcRepository<ExampleMysql, Long> {
}
