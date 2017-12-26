package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.service.EventService;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ExistingEventValidationRule implements Rule<Event> {
    @Autowired
    private EventService service;
    @Override
    public void validate(Event regData, Errors errors) {
        if(service.existingCollision(regData))
            errors.rejectValue("date", "negativvalue");
    }
}
