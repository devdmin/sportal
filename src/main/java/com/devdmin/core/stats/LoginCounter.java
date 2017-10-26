package com.devdmin.core.stats;



import com.devdmin.core.model.User;
import com.devdmin.core.security.AccountUserDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginCounter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @After(value = "execution(* com.devdmin.core.security.LoginAttemptService.loginSucceeded(..))")
    public void logLogin(JoinPoint joinPoint){
        User loggedUser = (User)joinPoint.getArgs()[1];

        logger.info("LOGIN: " + loggedUser.getUsername() + " IP: " + joinPoint.getArgs()[0]);

    }
}
