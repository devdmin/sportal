package com.devdmin.rest.controller;


import com.devdmin.core.model.Logging;
import com.devdmin.core.service.LoggingService;
import com.devdmin.core.service.util.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/stats")
@RestController
public class StatsController {
    @Autowired
    private LoggingService service;


    @RequestMapping("/logging/all")
    public ResponseEntity<List<Logging>> loggingsAll(){
        return new ResponseEntity<List<Logging>>(service.getLoggings(Period.ALL), HttpStatus.OK);
    }
}
