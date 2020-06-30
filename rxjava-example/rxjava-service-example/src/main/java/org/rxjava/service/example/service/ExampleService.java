package org.rxjava.service.example.service;

import org.bson.types.ObjectId;
import org.rxjava.service.example.entity.Example;
import org.rxjava.service.example.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class ExampleService {
    @Autowired
    private ExampleRepository exampleRepository;

    @Transactional
    public Mono<Example> testDemo(){
        ObjectId objectId = new ObjectId();
        Example example = new Example();
        example.setId(objectId);
        Example example1 = new Example();
        example1.setId(objectId);
        return exampleRepository.insert(example).then(exampleRepository.insert(example1));
    }
}
