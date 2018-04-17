package com.devdmin.rest.controller;

import com.devdmin.core.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
/**
 * @author Damian Ujma
 */
@Controller
public class MainController {
    private final UserService userService;
    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

    /* The user is logged in :) */
            return "dashboard";
        }
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users() {
        return "users";
    }

    @RequestMapping(value = "/control", method = RequestMethod.GET)
    public String adminController(){
        return "control";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String dashboard() {
        return "dashboard";
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats(){return "stats";}

    @RequestMapping(value = "/sportField", method = RequestMethod.GET)
    public String sportField() {
        return "sportfield";
    }

    @RequestMapping(value = "/user/{user}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String user(){return "user"; }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        return "index";
    }

    @GetMapping("/token/{token}")
    @PreAuthorize("permitAll")
    public String token(@PathVariable String token){
        UUID authToken = UUID.fromString(token);
        userService.authorizeUser(authToken);
        return "index";
    }

}
