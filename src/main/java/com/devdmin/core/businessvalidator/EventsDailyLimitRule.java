package com.devdmin.core.businessvalidator;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class EventsDailyLimitRule implements BusinessRule<Event,User> {
    @Autowired
    private UserService userService;

    private final long PERMISSIBLE_DAYS_BETWEEN_LAST_ADDING = 0;
    private final long EVENTS_PER_DAY = 2;

    @Override
    public boolean validateAdding(User user) {
        User foundUser = userService.find(user.getId());
        Optional<List<Event>> events = Optional.ofNullable(foundUser.getOwnEvents());

        if(events.isPresent()){
            Long todaysEvent = countEvetsByPermissibleDaysBeetweenLastAddingAndToday(events.get());

            if (todaysEvent > EVENTS_PER_DAY) {
                return false;
            }
        }
        return true;
    }

    private Long countEvetsByPermissibleDaysBeetweenLastAddingAndToday(List<Event> events){
        return events
                .stream()
                .filter(t -> ChronoUnit.DAYS.between(t.getAddingDate(), LocalDate.now()) == PERMISSIBLE_DAYS_BETWEEN_LAST_ADDING)
                .count();
    }
}
