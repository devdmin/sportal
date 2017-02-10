package com.devdmin.cores.repository;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFind()
    {
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
}
