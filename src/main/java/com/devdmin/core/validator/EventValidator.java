package com.devdmin.core.validator;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * This class provides a implementation of the {@link Validator}
 * interface for {@link EventDto} forms
 *
 * @author Damian Ujma
 */
@Component
public class EventValidator implements Validator {

    @Autowired
    private List<Rule<EventDto>> rules;

    @Override
    public boolean supports(Class<?> aClass) {
        return EventDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EventDto event = (EventDto) o;

        for(Rule<EventDto> rule : rules){
            rule.validate(event,errors);
        }
    }


}
