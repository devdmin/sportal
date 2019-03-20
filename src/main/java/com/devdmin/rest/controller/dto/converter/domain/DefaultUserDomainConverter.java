package com.devdmin.rest.controller.dto.converter.domain;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.rest.controller.dto.EventDto;
import com.devdmin.rest.controller.dto.SportFieldDto;
import com.devdmin.rest.controller.dto.UserDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDomainConverter implements BaseConverter<User, UserDto> {
    @Autowired
    private BaseConverter<Event, EventDto> eventDomainConverter;

    @Autowired
    private BaseConverter<SportField, SportFieldDto> sportFieldDomainConverter;

    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setAge(user.getAge());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setOwnEvents(eventDomainConverter.convertAll(user.getOwnEvents()));
        userDto.setOwnSportFields(sportFieldDomainConverter.convertAll(user.getOwnSportFields()));
        return userDto;
    }
}
