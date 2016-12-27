package com.devdmin.core.service.util;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.SportFieldType;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> userList = new ArrayList<User>();

    public UserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
