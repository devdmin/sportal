package com.devdmin.cores.validator.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.event.EventTimeRule;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventTimeRuleTest extends EventValidationTest {
    @Override
    public void setRule(Rule rule) {
        super.rule = new EventTimeRule();
    }

    @Test
    public void testValidationWithIllegalDate(){
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,01,00));
        rule.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidationWithIllegalStartMinutes(){
        event.setDate(LocalDateTime.of(2017,01,01,10,02));
        event.setEndDate(LocalDateTime.of(2017,01,01,11,00));
        rule.validate(event, errors);
        assertTrue(errors.hasErrors());
    }
    @Test
    public void testValidationWithIllegalEndMinutes(){
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,11,04));
        rule.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidLength(){
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,11,00));

        rule.validate(event,errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testIllegalMaxLength(){
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,20,00));

        rule.validate(event,errors);
        assertTrue(errors.hasErrors());
    }



    @Test
    public void testIllegalMinLength(){
        event.setDate(LocalDateTime.of(2017,01,01,10,00));
        event.setEndDate(LocalDateTime.of(2017,01,01,10,00));

        rule.validate(event,errors);
        assertTrue(errors.hasErrors());
    }
}
