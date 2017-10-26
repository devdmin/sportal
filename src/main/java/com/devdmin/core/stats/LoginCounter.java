package com.devdmin.core.stats;



import com.devdmin.core.model.Logging;
import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import com.devdmin.core.service.LoggingService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginCounter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoggingService service;

    @After(value = "execution(* com.devdmin.core.security.LoginAttemptService.loginSucceeded(..))")
    public void logLogin(JoinPoint joinPoint){
        User loggedUser = (User)joinPoint.getArgs()[1];
        service.logLogging(new Logging(loggedUser,(String) joinPoint.getArgs()[0]));
    }
}
