package com.devdmin.rest.controller;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;

import com.devdmin.core.service.UserService;
import com.devdmin.core.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;


@RequestMapping("api/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    private Validator userValidator;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> get(@PathVariable String username) {
        return Optional.ofNullable(userService.find(username))
                .map(user -> {
                    return new ResponseEntity<User>(user, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody @Valid User sentUser, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        } else {
            userService.addUser(sentUser);
            return new ResponseEntity<User>(sentUser, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> update(@PathVariable String username, @RequestBody User sentUser){
        return Optional.ofNullable(userService.find(username))
                .map(user -> {

                    User updatedUser = userService.update(user.getId(), sentUser);
                    return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<User> delete(@PathVariable String username){
        return Optional.ofNullable(userService.find(username))
                .map(user -> {
                    userService.delete(user.getId());
                    return new ResponseEntity<User>(user, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
        }
    }

//
//    @GetMapping
//    List<User> findAll(){
//        return userRepository.findAll();
//    }
//
//    @GetMapping("/{username}")
//     public ResponseEntity<User> find(@PathVariable String username){
//        return Optional.ofNullable(userRepository.findByUsername(username))
//                .map(user -> {
//                    return new ResponseEntity<>(user, HttpStatus.OK);
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

