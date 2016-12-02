package com.devdmin.rest.controller;

import com.devdmin.core.model.User;
import com.devdmin.core.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    UserRepository repository;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createUserNonExistingUsername() throws Exception {
        User createdUser = new User();

        createdUser.setUsername("test");
        createdUser.setEmail("test@test.test");
        createdUser.setPassword("testtest");

        when(repository.findByUsername("test")).thenReturn(createdUser);
        when(repository.save(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("api/users")
                .content("{\"username\":\"test\",\"password\":\"testtest\",\"email\":\"test@test.test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(createdUser.getUsername())))
                .andExpect(jsonPath("$.email", is(createdUser.getEmail())))
                .andExpect(status().isCreated());

    }


}

