package com.devdmin.core.service.impl;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public User find(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User find(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override

    public User addUser(User user) {
        user.setSignUpDate(LocalDate.now());
        return repository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        user.setId(id);
        repository.save(user);
        return user;
    }

    @Override
    public User delete(Long id) {
        User user = find(id);
        repository.delete(id);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }
}
