package com.devdmin.rest.controller.dto.converter.dto;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.rest.controller.dto.EventDto;
import com.devdmin.rest.controller.dto.SportFieldDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class DefaultSportFieldDtoConverter implements BaseConverter<SportFieldDto, SportField> {

    @Autowired
    private BaseConverter<EventDto, Event> eventDtoConverter;

    @Override
    public SportField convert(SportFieldDto sportFieldDto) {
        SportField sportField = new SportField();
        sportField.setId(sportFieldDto.getId());
        sportField.setLng(sportFieldDto.getLng());
        sportField.setLat(sportFieldDto.getLat());
        sportField.setType(sportFieldDto.getType());
        sportField.setAddingDate(sportFieldDto.getAddingDate());
        if(Optional.ofNullable(sportFieldDto.getEvents()).isPresent())
        sportField.setEvents(eventDtoConverter.convertAll(sportFieldDto.getEvents()));
        return sportField;
    }
}
