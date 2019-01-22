package com.devdmin.core.businessvalidator.event;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class EventCollisionRule implements BusinessRule<Event, User> {
    @Override
    public boolean validateAdding(Event event, User user) {
        if (event.getSportField() != null) {

            long eventsAmount = event.getSportField().getEvents()
                    .stream()
                    .filter(t -> t.getDate().isBefore(event.getDate().plusMinutes(1)))
                    .filter(t -> t.getEndDate().isAfter(event.getEndDate().minusMinutes(1)))
                    .count();
            eventsAmount = eventsAmount + event.getSportField().getEvents()
                    .stream()
                    .filter(t -> t.getDate().isBefore(event.getEndDate()))
                    .filter(t -> t.getDate().isAfter(event.getDate()))
                    .count();
            eventsAmount = eventsAmount + event.getSportField().getEvents()
                    .stream()
                    .filter(t -> t.getEndDate().isBefore(event.getEndDate()))
                    .filter(t -> t.getEndDate().isAfter(event.getDate()))
                    .count();
            eventsAmount = eventsAmount + event.getSportField().getEvents()
                    .stream()
                    .filter(t -> t.getEndDate().isBefore(event.getEndDate()))
                    .filter(t -> t.getDate().isAfter(event.getDate()))
                    .count();
            if (eventsAmount == 0) {
                return true;
            } else {
                return false;
            }

        }else if(event.getSportField().getEvents() == null){
            return true;
        }
        System.out.println("Kolizja");
        return false;

    }
}
