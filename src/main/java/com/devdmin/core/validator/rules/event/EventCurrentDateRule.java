package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventCurrentDateRule implements Rule<EventDto> {
    @Override
    public void validate(EventDto regData, Errors errors) {
        if(regData.getDate().isBefore(LocalDateTime.now()) || regData.getEndDate().isBefore(LocalDateTime.now())){
            errors.reject("date", "Date must be in the future");
        }
    }
}
