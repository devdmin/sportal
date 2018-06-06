package com.devdmin.core.validator.rules.sportfield;

import com.devdmin.core.model.SportField;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CoordsValidationRule implements Rule<SportField> {
    @Override
    public void validate(SportField regData, Errors errors) {
        //Coords for Poland
        if(!(regData.getLat() >  49.0063494 && regData.getLat() < 54.835881 && regData.getLng() > 14.122152 && regData.getLng() < 24.146168)){
            errors.rejectValue("lat", "negativevalue");
            errors.rejectValue("lng", "negativevalue");
        }



    }
}
