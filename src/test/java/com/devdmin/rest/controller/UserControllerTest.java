package com.devdmin.rest.controller;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.repository.UserRepository;
import com.devdmin.core.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private MockMvc mockMvc;
    private ArgumentCaptor<User> userCaptor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        userCaptor = ArgumentCaptor.forClass(User.class);
    }

    @Test
    public void createUser() throws Exception {
        User createdUser = new User();

        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");
        createdUser.setAge(16);

        when(service.addUser(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/users")
                .content("{\"username\":\"test\",\"password\":\"testtest\",\"email\":\"test@test.test\",\"age\":16}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(createdUser.getUsername())))
                .andExpect(jsonPath("$.email", is(createdUser.getEmail())))
                .andExpect(jsonPath("$.age", is(createdUser.getAge())))
                .andExpect(status().isCreated());
        verify(service).addUser(userCaptor.capture());
        String password = userCaptor.getValue().getPassword();
        assertEquals(createdUser.getPassword(),password);
    }

    @Test
    public void getUser() throws Exception{
        User user = new User();

        user.setUsername("test");
        user.setEmail("test@test.test");
        user.setPassword("testtest");
        user.setAge(16);


        when(service.find("test")).thenReturn(user);

        mockMvc.perform(get("/api/users/test"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser() throws Exception{
        User userA = new User();

        userA.setId(1L);
        userA.setUsername("testA");
        userA.setEmail("testA@test.test");
        userA.setPassword("testtestA");
        userA.setAge(16);

        User userB = new User();

        userB.setUsername("testA");
        userB.setEmail("testB@test.test");
        userB.setPassword("testtestB");
        userB.setAge(20);

        when(service.find("testA")).thenReturn(userA);
        when(service.update(1L,userB)).thenReturn(userB);

        mockMvc.perform(put("/api/users/testA")
                .content("{\"username\":\"testA\",\"password\":\"testtestB\",\"email\":\"testB@test.test\",\"age\":20}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsers() throws Exception{
        User userA = new User();

        userA.setUsername("testA");
        userA.setEmail("testA@test.test");
        userA.setPassword("testtestA");
        userA.setAge(16);

        User userB = new User();

        userB.setUsername("testB");
        userB.setEmail("testB@test.test");
        userB.setPassword("testtestB");
        userB.setAge(20);

        List<User> users = new ArrayList<>();
        users.add(userA);
        users.add(userB);

        when(service.findAll()).thenReturn(users);

//        mockMvc.perform(post("/api/users"))
//                .andExpect(jsonPath("$.usersList[*].username",
//                        hasItems(endsWith("testA"), endsWith("testB"))))
//                .andExpect(status().isOk());
    }
}

