package com.devdmin.core.validator;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * This class provides a implementation of the {@link Validator}
 * interface for {@link Event} forms
 *
 * @author Damian Ujma
 */
@Component
public class EventValidator implements Validator {

    @Autowired
    private List<Rule<Event>> rules;

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Event event = (Event) o;

        for(Rule<Event> rule : rules){
            rule.validate(event,errors);
        }
    }


}
