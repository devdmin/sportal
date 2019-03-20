package com.devdmin.rest.controller.dto.converter.dto;

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
public class DeafultUserDtoConverter implements BaseConverter<UserDto, User> {

    @Autowired
    private BaseConverter<EventDto, Event> eventDtoConverter;

    @Autowired
    private BaseConverter<SportFieldDto, SportField> sportFieldDtoConverter;

    public User convert(UserDto userDto) {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAge(userDto.getAge());

        return user;
    }
}
