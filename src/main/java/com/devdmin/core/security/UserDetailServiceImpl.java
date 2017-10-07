package com.devdmin.core.security;

import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.find(username);
        if(!userExists(user)) {
            throw new UsernameNotFoundException("no user found with " + username);
        }
        return new AccountUserDetails(user);
    }

    public boolean userExists(User user){
        return user == null ? false : true;
    }
}
