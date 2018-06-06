package com.devdmin.cores.validator.event;

import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.event.EventAgeRule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventAgeRuleTest extends EventValidationTest {

    @Test
    public void testValidationWithoutMinAndMaxAge(){
        event.setMinAge(0);
        event.setMaxAge(0);
        rule.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidAge(){
        event.setMinAge(10);
        event.setMaxAge(14);
        rule.validate(event, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testAgeOutOfRange(){
        event.setMinAge(-1);
        event.setMaxAge(200);
        rule.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testAgeOutOfRange2(){
        event.setMinAge(200);
        event.setMaxAge(-1);
        rule.validate(event, errors);
        assertTrue(errors.hasErrors());
    }

    void setRule(Rule rule) {
        super.rule = new EventAgeRule();
    }
}
