package com.devdmin.core.validator.rules;

import com.devdmin.core.model.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
@Component
public class EventAgeRule implements Rule<Event> {
    @Override
    public void validate(Event event, Errors errors) {
        if(event.getMinAge() < 6 || event.getMaxAge() > 100){
            errors.rejectValue("minAge", "negativevalue");
        }
    }
}
