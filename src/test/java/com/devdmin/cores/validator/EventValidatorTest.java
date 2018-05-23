package com.devdmin.cores.validator;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.service.EventService;
import com.devdmin.core.validator.EventValidator;
import com.devdmin.core.validator.rules.event.EventAgeRule;
import com.devdmin.core.validator.rules.event.EventFilledFieldsRule;
import com.devdmin.core.validator.rules.event.EventTimeRule;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.event.ExistingEventValidationRule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


public class EventValidatorTest {
    @InjectMocks
    private EventValidator eventValidator;

    @InjectMocks
    private ExistingEventValidationRule existingEventValidationRule;

    @Mock
    private EventService service;
    private Event event;
    private Errors errors;

    @Spy
    private List<Rule<Event>> rules = new ArrayList<Rule<Event>>();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        rules.add(new EventAgeRule());
        rules.add(new EventFilledFieldsRule());
        rules.add(new EventTimeRule());
        rules.add(existingEventValidationRule);
        event = makeValidEvent();
        errors = new BeanPropertyBindingResult(event, "createdEvent");

    }


    @Test
    public void testValidEvent() throws Exception{

        eventValidator.validate(event, errors);
        assertFalse(errors.hasErrors());
}

    @Test
    public void testValidationWithoutMinAndMaxAge() throws Exception{
        event.setMinAge(0);
        event.setMaxAge(0);
        eventValidator.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIllegalDate() throws Exception{
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,01,00));
        eventValidator.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIllegalAge() throws Exception{
        event.setMinAge(120);
        event.setMaxAge(0);

        eventValidator.validate(event, errors);
        assertFalse(errors.hasErrors());
    }
    @Test
    public void testValidationWithIllegalStartMinutes() throws Exception{
        event.setDate(LocalDateTime.of(2017,01,01,10,02));
        event.setEndDate(LocalDateTime.of(2017,01,01,01,00));

        eventValidator.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIllegalEndMinutes() throws Exception{
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,01,02));

        eventValidator.validate(event, errors);
        assertTrue(errors.hasErrors());
    }


    public Event makeValidEvent(){
        Event event = new Event();
        event.setMaxMembers(10);
        event.setGender(Gender.MALE);
        event.setMinAge(10);
        event.setMaxAge(16);
        event.setDate(LocalDateTime.of(2017,01,02,9,00));
        event.setEndDate(LocalDateTime.of(2017,01,02,12,00));
        return event;
    }


}
