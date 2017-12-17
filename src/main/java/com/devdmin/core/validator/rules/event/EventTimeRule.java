package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventTimeRule implements Rule<Event> {
    @Override
    public void validate(Event event, Errors errors) {
        if (event.getDate().isAfter(event.getEndDate())) {
            errors.reject("date", "negativevalue");
        }
        if (event.getDate().getMinute() == 30 || event.getDate().getMinute() == 0 &&
                event.getEndDate().getMinute() == 30 || event.getEndDate().getMinute() == 0){

        }else{
            errors.reject("date", "negativevalue");
        }
    }
}
