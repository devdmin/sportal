package com.devdmin.rest.controller;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{username}")
     public ResponseEntity<User> find(@PathVariable String username){
        return Optional.ofNullable(userRepository.findByUsername(username))
                .map(user -> {
                    return new ResponseEntity<>(user, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User sentUser) {
        sentUser.setSignUpDate(LocalDate.now());
        User user = userRepository.save(sentUser);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @DeleteMapping
    void delete(@PathVariable Long id){
        userRepository.delete(id);
    }
//
//    @PostMapping
//    User update(@RequestBody User sentUser){
//        User user = userRepository.findOne(sentUser.getId());
//        user.setPassword(sentUser.getPassword());
//        user.setEmail(sentUser.getEmail());
//        userRepository.save(user);
//        return user;
//    }
}
