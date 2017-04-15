package com.devdmin.rest.controller;

import com.devdmin.core.model.Event;
import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Matchers.any;

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

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void addEvent() throws Exception{
        SportField sportField = new SportField();
        sportField.setId(2L);
        sportField.setLat(42.445);
        sportField.setLng(13.335);
        sportField.setType(SportFieldType.VOLLEYBALL);


        Event event = new Event();
        event.setGender(Gender.MALE);
        event.setMinAge(10);
        event.setMaxAge(20);
        event.setMaxMembers(23);

        when(service.add(any(Event.class),any(Long.class))).thenReturn(event);

        mockMvc.perform(post("/api/events/2")
                .content("{\"gender\":\"MALE\",\"minAge\":\"10\",\"maxAge\":20}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gender", is(event.getGender().toString())))
                .andExpect(jsonPath("$.maxAge", is(event.getMaxAge())))
                .andExpect(jsonPath("$.minAge", is(event.getMinAge())))
                .andExpect(jsonPath("$.maxMembers", is(event.getMaxMembers())))
                .andExpect(status().isCreated());

    }

    @Test
    public void getAllEvents() throws Exception{
        Event eventA = new Event();
        eventA.setMinAge(10);
        eventA.setMaxAge(20);

        Event eventB = new Event();
        eventB.setMinAge(15);
        eventB.setMaxAge(22);

        List<Event> events = new ArrayList<>();
        events.add(eventA);
        events.add(eventB);

        when(service.findAll()).thenReturn(events);

        mockMvc.perform(get("/api/events"))
                .andExpect(jsonPath("$.events[*].minAge", hasItems(10,15)))
                .andExpect(jsonPath("$.events[*].maxAge", hasItems(20,22)))
                .andExpect(status().isOk());

    }

    @Test
    public void findBySportFieldId() throws Exception{
        Event eventA = new Event();
        eventA.setMinAge(10);
        eventA.setMaxAge(20);

        Event eventB = new Event();
        eventB.setMinAge(15);
        eventB.setMaxAge(22);

        List<Event> events = new ArrayList<>();
        events.add(eventA);
        events.add(eventB);

        when(service.findBySportFieldId(any(Long.class))).thenReturn(events);

        mockMvc.perform(get("/api/events/sportField/1"))
                .andExpect(jsonPath("$.events[*].minAge", hasItems(10,15)))
                .andExpect(jsonPath("$.events[*].maxAge", hasItems(20,22)))
                .andExpect(status().isOk());
    }

    @Test
    public void getEvent() throws Exception{
        Event event = new Event();
        event.setMinAge(10);
        event.setMaxAge(20);

        when(service.find(any(Long.class))).thenReturn(event);
        mockMvc.perform(get("/api/events/1"))
                .andExpect(jsonPath("$.maxAge", is(event.getMaxAge())))
                .andExpect(jsonPath("$.minAge", is(event.getMinAge())))
                .andExpect(status().isOk());
    }
}
