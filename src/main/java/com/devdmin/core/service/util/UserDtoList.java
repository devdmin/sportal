package com.devdmin.core.service.util;

import com.devdmin.rest.controller.dto.UserDto;

import java.util.HashSet;
import java.util.Set;

public class UserDtoList {
    private Set<UserDto> userList = new HashSet<UserDto>();

    public UserDtoList(Set<UserDto> userList) {
        this.userList = userList;
    }

    public Set<UserDto> getUserList() {
        return userList;
    }

    public void setUserList(Set<UserDto> userList) {
        this.userList = userList;
    }
}
