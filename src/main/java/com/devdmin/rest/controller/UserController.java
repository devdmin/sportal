package com.devdmin.rest.controller;


import com.devdmin.core.model.User;

import com.devdmin.core.service.UserService;
import com.devdmin.core.service.util.UserDtoList;
import com.devdmin.rest.controller.dto.UserDto;
import com.devdmin.rest.controller.dto.converter.BaseConverter;
import com.devdmin.rest.controller.dto.converter.dto.DeafultUserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

/**
 * @author Damian Ujma
*/
@RequestMapping("api/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    @Qualifier("userValidator")
    private Validator userValidator;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }


    @Autowired
    private BaseConverter<User, UserDto> userDomainConverter;

    @Autowired
    private BaseConverter<UserDto, User> userDtoConverter;

    @PostMapping()
    @PreAuthorize("permitAll")
    public ResponseEntity<UserDto> add(@RequestBody @Valid UserDto sentUser, BindingResult result) {
        System.out.println("OO" + userDtoConverter.toString());
        if (result.hasErrors()) {

            return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("XD " + userDtoConverter.convert(new UserDto()).toString());
            userService.addUser(userDtoConverter.convert(sentUser));
            return new ResponseEntity<UserDto>(sentUser, HttpStatus.CREATED);
        }
    }

    @GetMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<UserDtoList> findAll(){
        UserDtoList userList = new UserDtoList(userDomainConverter.convertAll(userService.findAll()));

        return new ResponseEntity<UserDtoList>(userList, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("permitAll")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        return Optional.ofNullable(userService.find(username))
                .map(user -> {
                    return new ResponseEntity<UserDto>(userDomainConverter.convert(user), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{username}")
    public ResponseEntity<UserDto> update(@PathVariable String username, @RequestBody @Valid User sentUser){
        return Optional.ofNullable(userService.find(username))
                .map(user -> {
                    User updatedUser = userService.update(user.getId(), sentUser);
                    return new ResponseEntity<UserDto>(userDomainConverter.convert(updatedUser), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<UserDto> delete(@PathVariable String username){
        return Optional.ofNullable(userService.find(username))
                .map(user -> {
                    userService.delete(user.getId());
                    return new ResponseEntity<UserDto>(userDomainConverter.convert(user), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> currentUser(){
        return new ResponseEntity<UserDto>(userDomainConverter.convert(userService.find(getAccountName())), HttpStatus.OK);
    }
    /**
     * Custom handler for displaying an account name.
     *
     * @return a String with current username
     */
    private String getAccountName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails details = (UserDetails) principal;
            return details.getUsername();
        }
        return null;
    }
}
