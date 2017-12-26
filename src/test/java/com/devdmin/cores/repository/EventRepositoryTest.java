package com.devdmin.cores.repository;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.repository.SportFieldRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)

public class EventRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    private Event event;

    @Before
    public void setup(){
        event = new Event(LocalDateTime.of(2017,11,13,14,15), LocalDateTime.of(2017,11,13,15,15), 20, 30, Gender.MALE, 22, new User());
    }

    @Test
    public void testFind() throws Exception{
      Long id = this.entityManager.persistAndGetId(event, Long.class);
      Event foundEvent = eventRepository.findOne(id);
      assertEquals(foundEvent.getId(), id);
    }

    @Test
    public void deleteTest() throws Exception{

        Long id = this.entityManager.persistAndGetId(event, Long.class);
        eventRepository.delete(id);
        Event foundEvent = eventRepository.findOne(id);
        assertNull(foundEvent);
    }
    @Test
    public void findBySportFieldIdTest() throws Exception{
             SportField sportField = new SportField(24.2444, 54.2555, SportFieldType.BASKETBALL);
            SportField foundSportField = entityManager.persist(sportField);
            Event event = new Event();
            event.setGender(Gender.MALE);
            event.setMinAge(10);
            event.setMaxAge(20);
            event.setDate(LocalDateTime.now());
            event.setSportField(foundSportField);

            Event event2 = new Event();
            event2.setGender(Gender.MALE);
            event2.setMinAge(15);
            event2.setMaxAge(20);
            event2.setDate(LocalDateTime.now());
            event2.setSportField(foundSportField);

            entityManager.persist(event);
            entityManager.persist(event2);
            entityManager.refresh(sportField);

        List<Event> foundEvents = eventRepository.findBySportField_Id(foundSportField.getId());
        assertEquals(event, foundEvents.get(0));
        assertEquals(event2, foundEvents.get(1));
    }

    @Test
    public void findEventsBetweenTwoDates() {
        Event event = new Event();
        Event event2 = new Event();
        event.setDate(LocalDateTime.now());
        event2.setDate(LocalDateTime.now().minusHours(1));

        entityManager.persist(event);
        entityManager.persist(event2);

        List<Event> foundEvents = eventRepository.findByDateBetween(event.getDate().minusDays(10),event.getDate().plusDays(10));
        //List<Event> foundEvents = eventRepository.findAll();
        logger.info("XD: " + foundEvents.size());

        //List<Event> event1 = eventRepository.findByDate(event.getDate());
       // logger.info("XD: " + event1.get(0).toString());
        //assertEquals(event, foundEvents.get(0));
        //assertEquals(event2, foundEvents.get(1));
    }
}
