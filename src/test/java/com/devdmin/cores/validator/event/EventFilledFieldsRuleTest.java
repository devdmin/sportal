package com.devdmin.cores.validator.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.event.EventFilledFieldsRule;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventFilledFieldsRuleTest extends EventValidationTest {
    @Override
    void setRule(Rule rule) {
        super.rule = new EventFilledFieldsRule();
    }

    @Test
    public void testWithFilledFields(){
        rule.validate(event,errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testWithEmptyFields(){
        event = new Event();
        errors = new BeanPropertyBindingResult(event, "createdEvent");
        rule.validate(event,errors);
        assertTrue(errors.hasErrors());
    }
}
