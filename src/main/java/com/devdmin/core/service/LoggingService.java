package com.devdmin.core.service;

import com.devdmin.core.model.Logging;
import com.devdmin.core.service.util.Period;

import java.util.List;

public interface LoggingService {
    void logLogging(Logging logging);
    List<Logging> getLoggings(Period period);
}
