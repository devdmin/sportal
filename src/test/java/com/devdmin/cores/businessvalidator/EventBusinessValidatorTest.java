package com.devdmin.cores.businessvalidator;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.businessvalidator.event.EventBusinessValidator;
import com.devdmin.core.businessvalidator.event.EventCollisionValidation;
import com.devdmin.core.businessvalidator.event.EventsDailyLimitRule;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class EventBusinessValidatorTest {
    @InjectMocks
    private EventBusinessValidator eventBusinessValidator;

    @InjectMocks
    private EventsDailyLimitRule limitRule;

    @Mock
    private UserService userService;

    private SportField busySportField;
    private Event event;
    @Spy
    private List<BusinessRule<Event, User>> rules = new ArrayList<BusinessRule<Event,User>>();

    private User user;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        rules.add(limitRule);
        rules.add(new EventCollisionValidation());

        user = new User("username","pass",24, Gender.MALE,"mail@mail.pl");
        user.setOwnEvents(new HashSet<>());
        when(userService.find(any(Long.class))).thenReturn(user);
        event = makeValidEvent();
        busySportField = createSportFieldWithEvent();
        event.setSportField(busySportField);
    }

    @Test
    public void testValidAddingEvents(){
        assertTrue(eventBusinessValidator.validateAdding(event,user));
    }

    @Test
    public void testIvalidAddingEvents(){
        Event event = new Event();
        event.setAddingDate(LocalDate.now());
        event.setMaxAge(23);
        Event event2 = new Event();
        event2.setAddingDate(LocalDate.now());
        event2.setMaxAge(24);
        Event event3 = new Event();
        event3.setMaxAge(25);
        event3.setAddingDate(LocalDate.now());
        user.setOwnEvents(new HashSet<>(Arrays.asList(event,event2,event3)));

        assertFalse(eventBusinessValidator.validateAdding(event, user));
    }

    //Busy SportField 2017-01-01 10:00 - 12:00
    @Test
    public void testEventWithBusySportField(){
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,11,59));

        assertFalse(eventBusinessValidator.validateAdding(event,user));
    }

    @Test
    public void testEventWithBusySportField2(){

        event.setDate(LocalDateTime.of(2017,01,01,9,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,10,59));
        assertFalse(eventBusinessValidator.validateAdding(event,user));
    }

    @Test
    public void testEventWithBusySportField3(){
        event.setDate(LocalDateTime.of(2017,01,01,11,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,13,00));
        assertFalse(eventBusinessValidator.validateAdding(event,user));
    }

    @Test
    public void testEventWithFreeSportField(){
        event.setDate(LocalDateTime.of(2017,01,01,12,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,13,00));
        assertTrue(eventBusinessValidator.validateAdding(event,user));
    }

    @Test
    public void testEventWithFreeSportField2(){

        event.setDate(LocalDateTime.of(2017,01,01,9,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,10,00));
        assertTrue(eventBusinessValidator.validateAdding(event,user));
    }

    public Event makeValidEvent(){
        Event event = new Event();
        event.setMaxMembers(10);
        event.setGender(Gender.MALE);
        event.setMinAge(10);
        event.setMaxAge(15);
        event.setDate(LocalDateTime.of(2017,01,02,9,00));
        event.setEndDate(LocalDateTime.of(2017,01,02,12,00));
        return event;
    }

    public SportField createSportFieldWithEvent(){
        SportField sportField = new SportField();
        Event anotherEvent = new Event();
        anotherEvent.setDate(LocalDateTime.of(2017,01,01,10,00));
        anotherEvent.setEndDate(LocalDateTime.of(2017,01,01,12,00));
        sportField.setEvents(new HashSet<Event>(Arrays.asList(anotherEvent)));
        anotherEvent.setSportField(sportField);
        return sportField;
    }
}
