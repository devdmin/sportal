package com.devdmin.cores.businessvalidator;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.businessvalidator.EventBusinessValidator;
import com.devdmin.core.businessvalidator.EventsDailyLimitRule;
import com.devdmin.core.model.Event;
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

    @Spy
    private List<BusinessRule<Event, User>> rules = new ArrayList<BusinessRule<Event,User>>();

    private User user;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        rules.add(limitRule);
        user = new User("username","pass",24, Gender.MALE,"mail@mail.pl");
    }

    @Test
    public void testValidAddingSportField(){
        Event event = new Event();

        user.setOwnEvents(new HashSet<>(Arrays.asList(event)));
        when(userService.find(any(Long.class))).thenReturn(user);
        assertTrue(eventBusinessValidator.validateAdding(user));
    }

    @Test
    public void testIvalidAddingSportField(){
        Event event = new Event();
        event.setAddingDate(LocalDate.now());
        Event event2 = new Event();
        event2.setAddingDate(LocalDate.now());
        Event event3 = new Event();
        event3.setAddingDate(LocalDate.now());
        user.setOwnEvents(new HashSet<>(Arrays.asList(event,event2,event3)));
        when(userService.find(any(Long.class))).thenReturn(user);
        assertFalse(eventBusinessValidator.validateAdding(user));
    }
}
