package com.devdmin.rest.controller;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("users")
@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    List<User> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("{username}")
    User findUser(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping
    User add(@RequestBody User sentUser) {
        sentUser.setSignUpDate(LocalDate.now());
        User user = userRepository.save(sentUser);
        return user;
    }

}
