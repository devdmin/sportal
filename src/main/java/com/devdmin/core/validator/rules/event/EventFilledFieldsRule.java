package com.devdmin.core.validator.rules.event;

import com.devdmin.core.model.Event;
import com.devdmin.core.validator.rules.Rule;
import com.devdmin.rest.controller.dto.EventDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.lang.reflect.Field;

@Component
public class EventFilledFieldsRule implements Rule<EventDto> {

    @Override
    public void validate(EventDto regData, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
        ValidationUtils.rejectIfEmpty(errors, "minAge", "minAge.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxAge", "maxAge.empty");
        ValidationUtils.rejectIfEmpty(errors, "date", "date.empty");
        ValidationUtils.rejectIfEmpty(errors, "endDate", "endDate.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxMembers", "maxMembers.empty");


    }
}
