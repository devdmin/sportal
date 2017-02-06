package com.devdmin.core.service;

import com.devdmin.core.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User find(Long id);
    User find(String username);
    User findByEmail(String email);
    User addUser(User user);
    User update(Long id, User user);
    User delete(Long id);
    List<User> findAll();
}
