package com.devdmin.cores.repository;

import com.devdmin.core.model.Event;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class SportFieldRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SportFieldRepository sportFieldRepository;

    @Autowired
    private UserRepository userRepository;



    @Test
    public void testFind() throws Exception{
        SportField sportField = new SportField();
        sportField.setLat(24.2444);
        sportField.setLng(54.2555);
        sportField.setType(SportFieldType.BASKETBALL);
        sportField.setVerified(false);
        sportField.setAddingDate(LocalDate.now());
        Long id = entityManager.persistAndGetId(sportField, Long.class);
        SportField foundSportField = sportFieldRepository.findOne(id);
        assertEquals(foundSportField.getId(), id);

    }


    @Test
    public void testDelete() throws Exception{
        SportField sportField = new SportField();
        sportField.setLat(24.2444);
        sportField.setLng(54.2555);
        sportField.setType(SportFieldType.BASKETBALL);
        sportField.setVerified(false);
        sportField.setAddingDate(LocalDate.now());
        Long id = entityManager.persistAndGetId(sportField, Long.class);
        sportFieldRepository.delete(id);
        SportField foundSportField = sportFieldRepository.findOne(id);
        assertNull(foundSportField);
    }

    @Test
    public void relationWithEventTest() throws Exception{
        SportField sportField = new SportField();
        sportField.setLat(24.2444);
        sportField.setLng(54.2555);
        sportField.setType(SportFieldType.BASKETBALL);
        sportField.setVerified(false);
        sportField.setAddingDate(LocalDate.now());
        SportField foundSportField = entityManager.persist(sportField);
        Event event = new Event();
        event.setGender(Gender.MALE);
        event.setMinAge(10);
        event.setMaxAge(20);
        event.setDate(LocalDateTime.now());
        event.setSportField(foundSportField);
        entityManager.persist(event);
        entityManager.refresh(sportField);

       Event foundEvent =sportFieldRepository.findOne(foundSportField.getId()).getEvents().get(0);

        assertEquals(event, foundEvent);
        assertEquals(event.getSportField(), foundSportField);
    }
    @Test
    public void relationWithUserTest() throws Exception{
        User user = new User();
        user.setUsername("Foo");
        user.setGender(Gender.MALE);
        user.setAge(20);
        user.setPassword("password");
        user.setEmail("email@email.email");
        user.setSignUpDate(LocalDate.now());
        SportField sportField = new SportField();
        sportField.setLat(24.2444);
        sportField.setLng(54.2555);
        sportField.setType(SportFieldType.BASKETBALL);
        sportField.setVerified(false);
        sportField.setAddingDate(LocalDate.now());
        List<SportField> list = new ArrayList<SportField>();
        list.add(sportField);
        user.setSportFields(list);
        User foundUser = entityManager.persist(user);
        entityManager.persist(foundUser);
        entityManager.persist(sportField);

        SportField foundSportField = userRepository.findOne(foundUser.getId()).getSportFields().get(0);
        assertEquals(foundSportField, sportField);
    }
}
