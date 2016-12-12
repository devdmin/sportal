package com.devdmin.rest.controller;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;

import com.devdmin.core.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;


@RequestMapping("api/users")
@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody @Valid User sentUser, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        } else {
            sentUser.setSignUpDate(LocalDate.now());
            userRepository.save(sentUser);
            return new ResponseEntity<User>(sentUser, HttpStatus.CREATED);
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



}
