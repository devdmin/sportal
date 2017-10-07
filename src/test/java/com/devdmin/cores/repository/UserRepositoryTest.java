package com.devdmin.cores.repository;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFind() throws Exception{
        User user = new User();
        user.setUsername("Foo");
        user.setGender(Gender.MALE);
        user.setAge(20);
        user.setPassword("password");
        user.setEmail("email@email.email");
        user.setSignUpDate(LocalDate.now());
        Long id = this.entityManager.persistAndGetId(user, Long.class);
        User foundUser = userRepository.findOne(id);
        assertEquals(user.getUsername(), foundUser.getUsername());

    }
    @Test
    public void testFindByUsername() throws Exception{
        User user = new User();
        user.setUsername("Foo");
        user.setGender(Gender.MALE);
        user.setAge(20);
        user.setPassword("password");
        user.setEmail("email@email.email");
        user.setSignUpDate(LocalDate.now());
        this.entityManager.persist(user);
        User foundUser = userRepository.findByUsername("Foo");
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    public void testFindByToken() throws Exception{
        User user = new User();
        user.setUsername("Foo");
        user.setGender(Gender.MALE);
        user.setAge(20);
        user.setPassword("password");
        user.setEmail("email@email.email");
        user.setSignUpDate(LocalDate.now());
        UUID token = UUID.randomUUID();
        user.setToken(token);
        this.entityManager.persist(user);
        User foundUser = userRepository.findByToken(token);

        logger.info("XDD: " + token);
        logger.info("XDD: " + foundUser.getToken());
        assertTrue(token.equals(foundUser.getToken()));

    }

    @Test
    public void testDelete() throws Exception{
        User user = new User();
        user.setUsername("Foo");
        user.setGender(Gender.MALE);
        user.setAge(20);
        user.setPassword("password");
        user.setEmail("email@email.email");
        user.setSignUpDate(LocalDate.now());
        Long id = this.entityManager.persistAndGetId(user, Long.class);
        userRepository.delete(id);
        User foundUser = userRepository.findOne(id);
        assertNull(foundUser);
    }

}
