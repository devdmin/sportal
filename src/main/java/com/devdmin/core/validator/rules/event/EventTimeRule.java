package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This class provides a implementation of the {@link Rule}
 * interface for {@link Event}
 * <p>
 * Stores errors for {@link Event} object.
 * <p>
 * @see Errors
 * @author Damian Ujma
 */
@Component
public class EventTimeRule implements Rule<EventDto> {
    @Override
    public void validate(EventDto event, Errors errors) {
        checkIfStartDateIsBeforeTheEnd(event,errors);
        checkIfTimeHasProperMinutes(event, errors);
        checkIfTimeHasProperLength(event, errors);

    }

    /**
      * Registers a global error for the entire target object when
      * the start date is after the end date
      */
    private void checkIfStartDateIsBeforeTheEnd(EventDto event, Errors errors){
        if (event.getDate().isAfter(event.getEndDate())) {
            errors.reject("date", "Start time is before end time");
        }
    }

    /**
     * Registers a global error for the entire target object when
     * event's minutes are not 00 or 30
     */
    private void checkIfTimeHasProperMinutes(EventDto event, Errors errors) {
        if ((event.getDate().getMinute() == 30 || event.getDate().getMinute() == 0) &&
                (event.getEndDate().getMinute() == 30 || event.getEndDate().getMinute() == 0)){

        }else{
            errors.reject("date", "Date has invalid minutes");
        }
    }

    /**
     * Registers a global error for the entire target object when
     * event's duration is not between 30 minutes and 4 hours
     */
    private void checkIfTimeHasProperLength(EventDto event, Errors errors) {
        long minutesLength = ChronoUnit.MINUTES.between(event.getDate(),event.getEndDate());
        long hoursLength = ChronoUnit.HOURS.between(event.getDate(),event.getEndDate());

        if(minutesLength < 30 || hoursLength > 4){
            errors.reject("date", "Duration has ivalid length");
        }
    }
}
