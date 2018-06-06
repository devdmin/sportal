package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
@Component
public class EventAgeRule implements Rule<Event> {
    @Override
    public void validate(Event event, Errors errors) {
        if(event.getMinAge() < 6 || event.getMinAge() > 100 || event.getMaxAge() < 6 || event.getMaxAge() > 100){
            errors.rejectValue("minAge", "negativevalue");
        }
    }
}
