package com.devdmin.core.validator.rules.sportfield;

import com.devdmin.core.geo.GeoCoderService;
import com.devdmin.core.geo.GeoService;
import com.devdmin.core.model.SportField;
import com.devdmin.core.validator.rules.Rule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CoordsValidationRule implements Rule<SportField> {
    @Override
    public void validate(SportField regData, Errors errors) {
        GeoService geoService = GeoCoderService.newInstance(regData.getLat(), regData.getLng());
        //Accept only coords for Poland
        if(!(geoService.getCountryName().equals("Poland"))){
            errors.rejectValue("lat", "negativevalue");
            errors.rejectValue("lng", "negativevalue");
        }



    }
}
