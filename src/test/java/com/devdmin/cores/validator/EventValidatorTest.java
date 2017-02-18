package com.devdmin.cores.validator;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.service.EventService;
import com.devdmin.core.validator.EventValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class EventValidatorTest {
    @InjectMocks
    private EventValidator eventValidator;

    @Mock
    private EventService service;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidEvent() throws Exception{
        Event createdEvent = new Event();
        createdEvent.setMaxMembers(10);
        createdEvent.setGender(Gender.MALE);
        createdEvent.setMinAge(10);
        createdEvent.setMaxAge(15);
        createdEvent.setDate(LocalDateTime.of(2017,01,01,10,00));
        createdEvent.setEndDate(LocalDateTime.of(2017,01,01,12,00));
        Errors errors = new BeanPropertyBindingResult(createdEvent, "createdEvent");

        eventValidator.validate(createdEvent, errors);
        assertFalse(errors.hasErrors());
}

    @Test
    public void testValidationWithoutMinAndMaxAge() throws Exception{
        Event createdEvent = new Event();
        createdEvent.setMaxMembers(10);
        createdEvent.setGender(Gender.MALE);
        createdEvent.setDate(LocalDateTime.of(2017,01,01,10,00));
        createdEvent.setEndDate(LocalDateTime.of(2017,01,01,12,00));
        Errors errors = new BeanPropertyBindingResult(createdEvent, "createdEvent");

        eventValidator.validate(createdEvent, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIllegalDate() throws Exception{
        Event createdEvent = new Event();
        createdEvent.setMaxMembers(10);
        createdEvent.setGender(Gender.MALE);
        createdEvent.setMinAge(10);
        createdEvent.setMaxAge(15);
        createdEvent.setDate(LocalDateTime.of(2017,01,01,10,00));
        createdEvent.setEndDate(LocalDateTime.of(2017,01,01,01,00));
        Errors errors = new BeanPropertyBindingResult(createdEvent, "createdEvent");

        eventValidator.validate(createdEvent, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIllegalAge() throws Exception{
        Event createdEvent = new Event();
        createdEvent.setMaxMembers(10);
        createdEvent.setGender(Gender.MALE);
        createdEvent.setMinAge(120);
        createdEvent.setMaxAge(0);
        createdEvent.setDate(LocalDateTime.of(2017,01,01,10,00));
        createdEvent.setEndDate(LocalDateTime.of(2017,01,01,12,00));
        Errors errors = new BeanPropertyBindingResult(createdEvent, "createdEvent");

        eventValidator.validate(createdEvent, errors);
        assertFalse(errors.hasErrors());
    }
    @Test
    public void testValidationWithIllegalStartMinutes() throws Exception{
        Event createdEvent = new Event();
        createdEvent.setMaxMembers(10);
        createdEvent.setGender(Gender.MALE);
        createdEvent.setMinAge(10);
        createdEvent.setMaxAge(15);
        createdEvent.setDate(LocalDateTime.of(2017,01,01,10,02));
        createdEvent.setEndDate(LocalDateTime.of(2017,01,01,01,00));
        Errors errors = new BeanPropertyBindingResult(createdEvent, "createdEvent");

        eventValidator.validate(createdEvent, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIllegalEndMinutes() throws Exception{
        Event createdEvent = new Event();
        createdEvent.setMaxMembers(10);
        createdEvent.setGender(Gender.MALE);
        createdEvent.setMinAge(10);
        createdEvent.setMaxAge(15);
        createdEvent.setDate(LocalDateTime.of(2017,01,01,10,00));
        createdEvent.setEndDate(LocalDateTime.of(2017,01,01,01,02));
        Errors errors = new BeanPropertyBindingResult(createdEvent, "createdEvent");

        eventValidator.validate(createdEvent, errors);
        assertTrue(errors.hasErrors());
    }
}
