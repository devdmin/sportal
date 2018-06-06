package com.devdmin.cores.validator.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.core.validator.rules.event.EventAgeRule;
import com.devdmin.cores.validator.ValidModelsGenerator;
import org.junit.Before;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

public abstract class EventValidationTest{
    protected Rule rule;
    protected Event event;
    protected Errors errors;

    abstract void setRule(Rule rule);

    @Before
    public void setup(){
        event = ValidModelsGenerator.generateValidEvent();
        errors = new BeanPropertyBindingResult(event, "createdEvent");
        setRule(rule);

    }
}
