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

    @RequestMapping("/logging/today")
    public ResponseEntity<List<Logging>> loggingsToday(){
        return new ResponseEntity<List<Logging>>(service.getLoggings(Period.TODAY), HttpStatus.OK);
    }
    @RequestMapping("/logging/week")
    public ResponseEntity<List<Logging>> loggingsWeek(){
        return new ResponseEntity<List<Logging>>(service.getLoggings(Period.WEEK), HttpStatus.OK);
    }
    @RequestMapping("/logging/year")
    public ResponseEntity<List<Logging>> loggingsYear(){
        return new ResponseEntity<List<Logging>>(service.getLoggings(Period.YEAR), HttpStatus.OK);
    }
    @RequestMapping("/logging/all")
    public ResponseEntity<List<Logging>> loggingsAll(){
        return new ResponseEntity<List<Logging>>(service.getLoggings(Period.ALL), HttpStatus.OK);
    }
}
