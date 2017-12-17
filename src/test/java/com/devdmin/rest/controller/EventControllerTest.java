package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.EventService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest {
    @InjectMocks
    private EventController controller;

    @Mock
    private EventService service;

    private MockMvc mockMvc;

    private Event event, eventB;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        event = new Event(LocalDateTime.of(2017,11,13,14,15), LocalDateTime.of(2017,11,13,15,15), 20, 30, Gender.MALE, 22, new User());
        eventB  = new Event(LocalDateTime.of(2017,11,13,14,15), LocalDateTime.of(2017,11,13,15,15), 10, 20, Gender.MALE, 20, new User());
    }

    @Test
    public void addEvent() throws Exception{

        AccountUserDetails applicationUser = mock(AccountUserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(service.add(any(Event.class),any(Long.class))).thenReturn(event);
        when((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);

        mockMvc.perform(post("/api/events/2")
                .content("{\"gender\":\"MALE\",\"minAge\":\"20\",\"maxAge\":30}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gender", is(event.getGender().toString())))
                .andExpect(jsonPath("$.maxAge", is(event.getMaxAge())))
                .andExpect(jsonPath("$.minAge", is(event.getMinAge())))
                .andExpect(jsonPath("$.maxMembers", is(event.getMaxMembers())))
                .andExpect(status().isCreated());

    }

    @Test
    public void getAllEvents() throws Exception{

        List<Event> events = new ArrayList<>();
        events.add(event);
        events.add(eventB);

        when(service.findAll()).thenReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(jsonPath("$.events[*].minAge", hasItems(20,20)))
                .andExpect(jsonPath("$.events[*].maxAge", hasItems(30,20)))
                .andExpect(status().isOk());

    }

    @Test
    public void findBySportFieldId() throws Exception{


        List<Event> events = new ArrayList<>();
        events.add(event);
        events.add(eventB);

        when(service.findBySportFieldId(any(Long.class))).thenReturn(events);

        mockMvc.perform(get("/api/events/sportField/1"))
                .andExpect(jsonPath("$.events[*].minAge", hasItems(20,20)))
                .andExpect(jsonPath("$.events[*].maxAge", hasItems(30,20)))
                .andExpect(status().isOk());
    }

    @Test
    public void getEvent() throws Exception{

        when(service.find(any(Long.class))).thenReturn(event);
        mockMvc.perform(get("/api/events/1"))
                .andExpect(jsonPath("$.maxAge", is(event.getMaxAge())))
                .andExpect(jsonPath("$.minAge", is(event.getMinAge())))
                .andExpect(status().isOk());
    }
}
