package com.devdmin.cores.businessvalidator.event;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.businessvalidator.event.EventCollisionRule;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.cores.validator.ValidModelsGenerator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventCollisionRuleTest {

    private BusinessRule<Event, User> rule = new EventCollisionRule();

    private Event event = ValidModelsGenerator.generateValidEvent();
    private User user = ValidModelsGenerator.generateValidUser();
    private SportField sportField = new SportField();

    @Before
    public void setUp(){
        Event addedEvent = new Event();
        addedEvent.setDate(LocalDateTime.of(2018,02,02,12,00));
        addedEvent.setEndDate(LocalDateTime.of(2018,02,02,14,00));
        sportField.setEvents(new HashSet<Event>(Arrays.asList(addedEvent)));
        event.setSportField(sportField);
    }

    @Test
    public void testWithoutCollision(){
        event.setDate(LocalDateTime.of(2018,02,02,14,00));
        event.setEndDate(LocalDateTime.of(2018,02,02,16,00));

        assertTrue(rule.validateAdding(event, user));

        event.setDate(LocalDateTime.of(2018,02,02,10,00));
        event.setEndDate(LocalDateTime.of(2018,02,02,12,00));

        assertTrue(rule.validateAdding(event, user));

        event.setDate(LocalDateTime.of(2018,02,02,18,00));
        event.setEndDate(LocalDateTime.of(2018,02,02,20,00));

        assertTrue(rule.validateAdding(event, user));
    }

    @Test
    public void testWithCollision(){
        event.setDate(LocalDateTime.of(2018,02,02,12,00));
        event.setEndDate(LocalDateTime.of(2018,02,02,16,00));

        assertFalse(rule.validateAdding(event, user));

        event.setDate(LocalDateTime.of(2018,02,02,10,00));
        event.setEndDate(LocalDateTime.of(2018,02,02,12,30));

        assertFalse(rule.validateAdding(event, user));

        event.setDate(LocalDateTime.of(2018,02,02,12,00));
        event.setEndDate(LocalDateTime.of(2018,02,02,14,30));

        assertFalse(rule.validateAdding(event, user));

        event.setDate(LocalDateTime.of(2018,02,02,12,00));
        event.setEndDate(LocalDateTime.of(2018,02,02,14,00));

        assertFalse(rule.validateAdding(event, user));
    }


}
