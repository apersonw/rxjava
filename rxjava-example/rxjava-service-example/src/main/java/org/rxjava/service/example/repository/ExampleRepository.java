package org.rxjava.service.example.repository;

import org.bson.types.ObjectId;
import org.rxjava.service.example.entity.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends ReactiveMongoRepository<Example, ObjectId> {
}
