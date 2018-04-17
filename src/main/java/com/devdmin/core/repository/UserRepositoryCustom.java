package com.devdmin.core.repository;

import com.devdmin.core.model.User;

import java.util.UUID;

/**
 * Custom repository interface for <code>User</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Damian Ujma
 */
public interface UserRepositoryCustom {
    User findByToken(UUID token);
}
