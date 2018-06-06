package com.devdmin.cores.validator.event;

import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.event.EventCurrentDateRule;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class EventCurrentDateRuleTest extends EventValidationTest {
    @Override
    void setRule(Rule rule) {
        super.rule = new EventCurrentDateRule();
    }

    @Test
    public void testValidEventDate(){
        event.setDate(LocalDateTime.now().plusHours(2));
        event.setEndDate(LocalDateTime.now().plusHours(4));
        rule.validate(event, errors);

        Assert.assertFalse(errors.hasErrors());
    }

    @Test
    public void testIllegalEventDate(){
        event.setDate(LocalDateTime.now().minusHours(4));
        event.setEndDate(LocalDateTime.now().minusHours(1));
        rule.validate(event, errors);

        Assert.assertTrue(errors.hasErrors());
    }
}
