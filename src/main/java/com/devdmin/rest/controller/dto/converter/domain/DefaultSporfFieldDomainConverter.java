package com.devdmin.rest.controller.dto.converter.domain;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.rest.controller.dto.EventDto;
import com.devdmin.rest.controller.dto.SportFieldDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultSporfFieldDomainConverter implements BaseConverter<SportField, SportFieldDto> {

    @Autowired
    private BaseConverter<Event, EventDto> eventDomainConverter;

    public SportFieldDto convert(SportField sportField) {

        SportFieldDto sportFieldDto = new SportFieldDto();
        sportFieldDto.setId(sportField.getId());
        sportFieldDto.setLat(sportField.getLat());
        sportFieldDto.setLng(sportField.getLng());
        sportFieldDto.setType(sportField.getType());
        sportFieldDto.setEvents(eventDomainConverter.convertAll(sportField.getEvents()));
        return sportFieldDto;
    }
}
