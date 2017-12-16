package com.devdmin.rest.controller;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.SportFieldService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SportFieldControllerTest {
    @InjectMocks
    private SportFieldController controller;

    @Mock
    private SportFieldService service;

    private MockMvc mockMvc;

    private SportField sportField;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        sportField = new SportField();
        sportField.setLat(42.445);
        sportField.setLng(13.335);
        sportField.setType(SportFieldType.BASKETBALL);
    }

    @Test
    public void addSportField() throws Exception{
        AccountUserDetails applicationUser = mock(AccountUserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(service.add(any(SportField.class))).thenReturn(sportField);
        when((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
        mockMvc.perform(post("/api/sportField")
                .content("{\"lat\":\"42.445\",\"type\":\"VOLLEYBALL\",\"lng\":13.335}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lat", is(sportField.getLat())))
                .andExpect(jsonPath("$.lng", is(sportField.getLng())))
                .andExpect(jsonPath("$.type", is(sportField.getType().toString())))
                .andExpect(status().isCreated());
    }



    @Test
    public void getSportField() throws Exception{
        when(service.find(1L)).thenReturn(sportField);
        mockMvc.perform(get("/api/sportField/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateSportField() throws Exception{
        SportField sportFieldB = new SportField();
        sportFieldB.setLat(24.445);
        sportFieldB.setLng(31.335);
        sportFieldB.setType(SportFieldType.VOLLEYBALL);

        when(service.find(any(Long.class))).thenReturn(sportField);
        when(service.update(any(Long.class), any(SportField.class))).thenReturn(sportFieldB);

        mockMvc.perform(put("/api/sportField/1")
                .content("{\"lat\":\"24.445\",\"type\":\"VOLLEYBALL\",\"lng\":31.335}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lat", is(sportFieldB.getLat())))
                .andExpect(jsonPath("$.lng", is(sportFieldB.getLng())))
                .andExpect(jsonPath("$.type", is(sportFieldB.getType().toString())))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void deleteSportField() throws Exception{
       when(service.find(1L)).thenReturn(sportField);
        mockMvc.perform(delete("/api/sportField/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
