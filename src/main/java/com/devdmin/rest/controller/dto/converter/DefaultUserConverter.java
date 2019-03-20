package com.devdmin.rest.controller.dto.converter;

import com.devdmin.core.model.User;
import com.devdmin.rest.controller.dto.UserDto;
import org.springframework.stereotype.Component;


@Component
public class DefaultUserConverter implements BaseConverter<User, UserDto> {

    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getPassword());
        userDto.setGender(user.getGender());
        userDto.setAge(user.getAge());
        return userDto;
    }
}
