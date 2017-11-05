package com.devdmin.core.service.impl;

import com.devdmin.core.model.Logging;
import com.devdmin.core.repository.LoggingRepository;
import com.devdmin.core.service.LoggingService;
import com.devdmin.core.service.util.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggingServiceImpl implements LoggingService {
    @Autowired
    private LoggingRepository repository;

    @Override
    public void logLogging(Logging logging) {
        logging.setDate(LocalDateTime.now());
        repository.save(logging);
    }

    @Override
    public List<Logging> getLoggings(Period period) {
        List<Logging> loggings = repository.findAll();
        switch(period){
            case TODAY:
                return loggings.stream()
                        .filter(logging -> ChronoUnit.DAYS.between(logging.getDate(),LocalDateTime.now()) == 0)
                        .collect(Collectors.toList());

            case WEEK:
                return loggings.stream()
                        .filter(logging -> ChronoUnit.WEEKS.between(logging.getDate(),LocalDateTime.now()) == 0)
                        .collect(Collectors.toList());
            case YEAR:
                return loggings.stream()
                        .filter(logging -> ChronoUnit.YEARS.between(logging.getDate(),LocalDateTime.now()) == 0)
                        .collect(Collectors.toList());
            case ALL:
                return loggings;
            default:
                return loggings;
        }
    }


}
