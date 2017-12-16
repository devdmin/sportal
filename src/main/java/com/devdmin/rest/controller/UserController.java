package com.devdmin.rest.controller;

import com.devdmin.core.businessvalidator.BusinessValidator;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.repository.UserRepository;

import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.UserList;
import com.devdmin.core.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
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


    @PostMapping()
    @PreAuthorize("permitAll")
    public ResponseEntity<User> add(@RequestBody @Valid User sentUser, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        } else {
            userService.addUser(sentUser);
            return new ResponseEntity<User>(sentUser, HttpStatus.CREATED);
        }
    }

    @GetMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<UserList> findAll(){
        UserList userList = new UserList(userService.findAll());
        return new ResponseEntity<UserList>(userList, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("permitAll")
    public ResponseEntity<User> get(@PathVariable String username) {
        return Optional.ofNullable(userService.find(username))
                .map(user -> {
                    return new ResponseEntity<User>(user, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{username}")
    public ResponseEntity<User> update(@PathVariable String username, @RequestBody @Valid User sentUser){
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

    @GetMapping("/user")
    public ResponseEntity<User> currentUser(){
        return new ResponseEntity<User>(userService.find(getAccountName()), HttpStatus.OK);
    }

    private String getAccountName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            return details.getUsername();
        }
        return null;
    }
}
