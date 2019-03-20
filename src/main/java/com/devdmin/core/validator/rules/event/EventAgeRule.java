package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
@Component
public class EventAgeRule implements Rule<EventDto> {
    @Override
    public void validate(EventDto event, Errors errors) {
        if(event.getMinAge() < 6 || event.getMinAge() > 100 || event.getMaxAge() < 6 || event.getMaxAge() > 100){
            errors.rejectValue("minAge", "Invalid age");
        }
    }
}
