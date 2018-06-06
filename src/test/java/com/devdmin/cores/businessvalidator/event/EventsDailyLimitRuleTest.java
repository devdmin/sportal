package com.devdmin.cores.businessvalidator.event;

import com.devdmin.core.businessvalidator.event.EventsDailyLimitRule;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import com.devdmin.cores.validator.ValidModelsGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.devdmin.cores.validator.ValidModelsGenerator.generateRandomEvents;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class EventsDailyLimitRuleTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private EventsDailyLimitRule rule;

    private User user;
    private Event event;
    private final int MAX_EVENTS_PER_DAY = 2;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        user = ValidModelsGenerator.generateValidUser();
        event = ValidModelsGenerator.generateValidEvent();
    }

    @Test
    public void testEventsDailyLimitRule(){
        MockitoAnnotations.initMocks(this);
        user.setOwnEvents(generateRandomEvents(MAX_EVENTS_PER_DAY+1));
        when(userService.find(any(Long.class))).thenReturn(user);
        assertFalse(rule.validateAdding(event, user));
    }

    @Test
    public void testEventsValidDailyLimitRule(){
        MockitoAnnotations.initMocks(this);
        user.setOwnEvents(generateRandomEvents(MAX_EVENTS_PER_DAY));
        when(userService.find(any(Long.class))).thenReturn(user);
        assertTrue(rule.validateAdding(event, user));
    }

}
