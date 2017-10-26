package com.devdmin.core.service.impl;

import com.devdmin.core.model.Logging;
import com.devdmin.core.repository.LoggingRepository;
import com.devdmin.core.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggingServiceImpl implements LoggingService {
    @Autowired
    private LoggingRepository repository;

    @Override
    public void logLogging(Logging logging) {
        logging.setDate(LocalDateTime.now());
        repository.save(logging);
    }
}
