package com.devdmin.core.businessvalidator.event;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class EventsDailyLimitRule implements BusinessRule<Event,User> {
    @Autowired
    private UserService userService;

    private final long PERMISSIBLE_DAYS_BETWEEN_LAST_ADDING = 0;
    private final long MAX_EVENTS_PER_DAY = 2;

    @Override
    public boolean validateAdding(Event event, User user) {
        if(user.getUsername().equals("admin")) {
            return true;
        }
        User foundUser = userService.find(user.getId());
        Optional<Set<Event>> events = Optional.ofNullable(foundUser.getOwnEvents());
        if(events.isPresent()){
            Long todaysEvent = countEvetsByPermissibleDaysBeetweenLastAddingAndToday(events.get());
            if (todaysEvent > MAX_EVENTS_PER_DAY) {
                System.out.println("Limit");

                return false;
            }else{
                return true;
            }
        }
        return true;
    }

    private Long countEvetsByPermissibleDaysBeetweenLastAddingAndToday(Set<Event> events){
        return events
                .stream()
                .filter(t -> ChronoUnit.DAYS.between(t.getAddingDate(), LocalDate.now()) == PERMISSIBLE_DAYS_BETWEEN_LAST_ADDING)
                .count();
    }
}
