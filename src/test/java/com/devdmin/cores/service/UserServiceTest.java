package com.devdmin.cores.service;


import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.impl.UserServiceImpl;
import com.devdmin.core.service.util.VerificationLinksSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    VerificationLinksSender sender;
    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserServiceImpl service;

    @Test
    public void findTest(){
        when(userRepository.findOne(any(Long.class))).thenReturn(any(User.class));
        service.find(1L);
        Mockito.verify(userRepository, Mockito.times(1)).findOne(any(Long.class));
    }

    @Test
    public void addUserTest(){
        User user = new User();
        user.setPassword("password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(sender).send(any(UUID.class),any(String.class));
        service.addUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));

    }

    @Test
    public void addSportFieldTest(){
        User user = new User();
        SportField sportField = new SportField();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findOne(any(Long.class))).thenReturn(user);
        service.addSportField(sportField, user);

        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

}
