package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventCurrentDateRule implements Rule<Event> {
    @Override
    public void validate(Event regData, Errors errors) {
        if(regData.getDate().isBefore(LocalDateTime.now()) || regData.getEndDate().isBefore(LocalDateTime.now())){
            errors.reject("date", "Date must be in the future");
        }
    }
}
