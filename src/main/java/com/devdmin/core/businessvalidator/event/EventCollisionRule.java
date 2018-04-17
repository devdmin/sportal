package com.devdmin.core.businessvalidator.event;


import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;

public class EventCollisionRule implements BusinessRule<Event,User> {
    @Override
    public boolean validateAdding(User user) {
        return false;
    }
}
