package org.rxjava.security.example.repository;

import org.rxjava.security.example.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author happy 2019-04-27 00:13
 */
@Repository
public interface RoleRepository extends ReactiveSortingRepository<Role, String>, SpecialRoleRepository {
}

interface SpecialRoleRepository {

}

class SpecialRoleRepositoryImpl implements SpecialRoleRepository {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
}