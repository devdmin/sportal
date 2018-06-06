package com.devdmin.cores.validator.sportfield;

import com.devdmin.core.model.SportField;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.sportfield.SportFieldFilledFieldsRule;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SportFieldFilledFieldsRuleTest extends SportFieldValidationTest {
    @Override
    void setRule(Rule rule) {
        super.rule = new SportFieldFilledFieldsRule();
    }

    @Test
    public void testValidation(){
        rule.validate(sportField, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testValidationWithoutFilledFields(){
        sportField = new SportField();
        errors = new BeanPropertyBindingResult(sportField, "createdEvent");

        rule.validate(sportField, errors);
        assertTrue(errors.hasErrors());
    }
}
