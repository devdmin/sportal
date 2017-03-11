package com.devdmin.core.service.impl;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private JavaMailSender mailSender;


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
        String token = UUID.randomUUID().toString();
        user.setSignUpDate(LocalDate.now());
        user.setVerified(false);
        user.setToken(token);

        String recipientAddress = user.getEmail();
        String subject = "Potwierdzenie rejestracji";
        String confirmationUrl = "http://localhost:8080/token/" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        mailSender.send(email);
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
    public void authorizeUser(String token) {
        User user = repository.findByToken(token);
        user.setVerified(true);
        repository.save(user);
    }
}
