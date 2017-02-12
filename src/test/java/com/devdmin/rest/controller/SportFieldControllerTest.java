package com.devdmin.rest.controller;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.util.SportFieldType;
import com.devdmin.core.service.SportFieldService;
import com.devdmin.core.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    public void getSportField() throws Exception{
        SportField sportField = new SportField();
        sportField.setLat(42.445);
        sportField.setLng(13.335);
        sportField.setType(SportFieldType.BASKETBALL);

        when(service.find(1L)).thenReturn(sportField);

        mockMvc.perform(get("/api/sportField/1"))
                .andExpect(status().isOk());
    }
}
