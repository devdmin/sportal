package com.devdmin.core.service.impl;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.VerificationLinksSender;
import com.devdmin.core.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public SportField addSportField(SportField sportField, User user) {

            User foundUser = repository.findOne(user.getId());
            List<SportField> sportFields = Optional.ofNullable(foundUser.getOwnSportFields())
                    .orElse(new ArrayList<SportField>());

            Optional<SportField> todaysSportField = sportFields
                                    .stream()
                                    .filter(t -> ChronoUnit.DAYS.between(t.getAddingDate(),LocalDate.now())==0)
                                    .findAny();

        if(!todaysSportField.isPresent()) {
            sportField.setAuthor(foundUser);
            sportFields.add(sportField);
            repository.save(foundUser);
        }

        return sportField;

    }

    @Override
     public User getLoggedUser() {
        AccountUserDetails user = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
