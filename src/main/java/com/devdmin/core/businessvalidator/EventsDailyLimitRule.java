package com.devdmin.core.businessvalidator;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class EventsDailyLimitRule implements BusinessRule<Event,User> {
    @Override
    public boolean validate(Event event, User user) {
        return false;
    }
}
