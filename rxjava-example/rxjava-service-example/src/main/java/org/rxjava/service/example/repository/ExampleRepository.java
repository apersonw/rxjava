package org.rxjava.service.example.repository;

import org.bson.types.ObjectId;
import org.rxjava.service.example.entity.Example;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends ReactiveSortingRepository<Example, ObjectId> {
}
