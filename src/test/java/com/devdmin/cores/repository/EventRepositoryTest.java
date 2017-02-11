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

    @Autowired
    private SportFieldRepository sportFieldRepository;
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
}
