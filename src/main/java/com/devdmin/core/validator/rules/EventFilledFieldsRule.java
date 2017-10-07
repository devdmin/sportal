package com.devdmin.core.validator.rules;

import com.devdmin.core.model.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
@Component
public class EventFilledFieldsRule implements Rule<Event>{

    @Override
    public void validate(Event regData, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
        ValidationUtils.rejectIfEmpty(errors, "minAge", "minAge.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxAge", "maxAge.empty");
        ValidationUtils.rejectIfEmpty(errors, "date", "date.empty");
        ValidationUtils.rejectIfEmpty(errors, "endDate", "endDate.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxMembers", "maxMembers.empty");
    }
}
