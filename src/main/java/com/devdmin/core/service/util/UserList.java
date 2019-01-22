package com.devdmin.core.service.util;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.SportFieldType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserList {
    private Set<User> userList = new HashSet<User>();

    public UserList(Set<User> userList) {
        this.userList = userList;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }
}
