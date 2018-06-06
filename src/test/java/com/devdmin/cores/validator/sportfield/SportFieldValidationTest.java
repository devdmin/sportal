package com.devdmin.cores.validator.sportfield;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.cores.validator.ValidModelsGenerator;
import org.junit.Before;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public abstract class SportFieldValidationTest {
    protected Rule rule;
    protected SportField sportField;
    protected Errors errors;

    abstract void setRule(Rule rule);

    @Before
    public void setup(){
        sportField = ValidModelsGenerator.generateValidSportField();
        errors = new BeanPropertyBindingResult(sportField, "createdEvent");
        setRule(rule);

    }
}
