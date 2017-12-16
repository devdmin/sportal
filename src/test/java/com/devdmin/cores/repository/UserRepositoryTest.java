package com.devdmin.cores.repository;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.SportFieldRepository;
import com.devdmin.core.repository.UserRepository;
import org.junit.Before;
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

    private User user;

    @Before
    public void setup(){
        user = new User("username","pass",24, Gender.MALE,"mail@mail.pl");
    }
    @Test
    public void testFind() throws Exception{

        Long id = this.entityManager.persistAndGetId(user, Long.class);
        User foundUser = userRepository.findOne(id);
        assertEquals(user.getUsername(), foundUser.getUsername());

    }
    @Test
    public void testFindByUsername() throws Exception{
        this.entityManager.persist(user);
        User foundUser = userRepository.findByUsername(user.getUsername());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    public void testFindByToken() throws Exception{
        this.entityManager.persist(user);
        User foundUser = userRepository.findByToken(user.getToken());
        assertTrue(user.getToken().equals(foundUser.getToken()));
    }

    @Test
    public void testDelete() throws Exception{
        Long id = this.entityManager.persistAndGetId(user, Long.class);
        userRepository.delete(id);
        User foundUser = userRepository.findOne(id);
        assertNull(foundUser);
    }

}
