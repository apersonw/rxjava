package org.rxjava.security.example.repository;

import org.rxjava.security.example.entity.ManagerRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author happy 2019-04-27 00:13
 */
@Repository
public interface ManagerRoleRepository extends ReactiveSortingRepository<ManagerRole, String>, SpecialManagerRoleRepository {
}

interface SpecialManagerRoleRepository {
}

class SpecialManagerRoleRepositoryImpl implements SpecialManagerRoleRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
}