package com.ajp64.hockeytracker.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class SecurityAspect {

    private final Logger logger = Logger.getLogger(SecurityAspect.class.getName());

    @Before(value = "@annotation(SecurityCheck)")
    void security() throws Throwable {
        logger.info("Security Execution");
    }
}
