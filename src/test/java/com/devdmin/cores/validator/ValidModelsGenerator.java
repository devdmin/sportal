package com.devdmin.cores.validator;

import com.devdmin.core.businessvalidator.BusinessRule;
import com.devdmin.core.businessvalidator.event.EventsDailyLimitRule;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.impl.SportFieldServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ValidModelsGenerator {

    public static User generateValidUser(){
        return new User("username", "password", 21, Gender.MALE, "mail@mail.mail");
    }

    public static Event generateValidEvent() {
        Event event = new Event();
        event.setMaxMembers(10);
        event.setGender(Gender.MALE);
        event.setMinAge(10);
        event.setMaxAge(15);
        event.setDate(LocalDateTime.of(2017,01,02,9,00));
        event.setEndDate(LocalDateTime.of(2017,01,02,10,00));
        return event;
    }

    public static SportField generateValidSportField(){
        return new SportField(51.058318, 19.451410, SportFieldType.FOOTBALL);
    }

    public static Set<Event> generateRandomEvents(int amount) {
        Set<Event> randomEvents = new HashSet<Event>(amount);

        for(int i = 0 ; i < amount; i++){
            Event event = new Event();
            event.setId(Long.valueOf(i));
            randomEvents.add(event);
        }
        return randomEvents;
    }
}
