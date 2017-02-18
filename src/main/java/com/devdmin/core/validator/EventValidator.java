package com.devdmin.core.validator;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.service.EventService;
import com.devdmin.core.service.SportFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventValidator implements Validator {
    @Autowired
    private EventService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Event.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
        ValidationUtils.rejectIfEmpty(errors, "minAge", "minAge.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxAge", "maxAge.empty");
        ValidationUtils.rejectIfEmpty(errors, "date", "date.empty");
        ValidationUtils.rejectIfEmpty(errors, "endDate", "endDate.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxMembers", "maxMembers.empty");

        Event event = (Event) o;
        if(event.getMinAge() < 6 || event.getMaxAge() > 100){
            errors.rejectValue("minAge", "negativevalue");
        }
        if (event.getDate().isAfter(event.getEndDate())) {
            errors.reject("date", "negativevalue");
        }
        if (event.getDate().getMinute() == 30 || event.getDate().getMinute() == 0 &&
                 event.getEndDate().getMinute() == 30 || event.getEndDate().getMinute() == 0){

        }else{
            errors.reject("date", "negativevalue");
        }


    }
}
