package com.devdmin.core.repository;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * Implementation of custom repository interface for <code>User</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Damian Ujma
 */
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByToken(UUID token) {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if(user.getToken().equals(token))
                return user;
        }
        throw new NullPointerException();
    }
}
