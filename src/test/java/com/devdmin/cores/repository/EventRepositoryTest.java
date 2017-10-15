package com.devdmin.cores.repository;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.EventRepository;
import com.devdmin.core.repository.SportFieldRepository;
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

import static org.junit.Assert.assertEquals;
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

    @Test
    public void testFind() throws Exception{
      Event event = new Event();
      event.setGender(Gender.MALE);
      event.setMinAge(10);
      event.setMaxAge(20);
      event.setDate(LocalDateTime.now());
      Long id = this.entityManager.persistAndGetId(event, Long.class);
      Event foundEvent = eventRepository.findOne(id);
      assertEquals(foundEvent.getId(), id);
    }

    @Test
    public void deleteTest() throws Exception{
        Event event = new Event();
        event.setGender(Gender.MALE);
        event.setMinAge(10);
        event.setMaxAge(20);
        event.setDate(LocalDateTime.now());
        Long id = this.entityManager.persistAndGetId(event, Long.class);
        eventRepository.delete(id);
        Event foundEvent = eventRepository.findOne(id);
        assertNull(foundEvent);
    }
    @Test
    public void findBySportFieldIdTest() throws Exception{
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
}
