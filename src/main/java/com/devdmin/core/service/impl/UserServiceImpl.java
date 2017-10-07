package com.devdmin.core.service.impl;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.VerificationLinksSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;
    @Autowired
    private VerificationLinksSender sender;
    @Override
    public User save(User user) {

        return repository.save(user);
    }

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
        UUID token = UUID.randomUUID();
        user.setSignUpDate(LocalDate.now());
        user.setVerified(false);
        user.setToken(token);
        sender.send(token, user.getEmail());
        return repository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        user.setId(id);
        return repository.save(user);
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

    @Override
    public void authorizeUser(UUID token) {
        User user = repository.findByToken(token);
        user.setVerified(true);
        repository.save(user);
    }
}
