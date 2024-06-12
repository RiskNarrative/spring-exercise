package com.example.SpringExercise.Configuration;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Pointcut("execution(* com.example.SpringExercise.Service.*.*(..))")
    public void applicationServicePointcut() {
        // Pointcut for all methods in service package
    }

    @Before("applicationServicePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "applicationServicePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method: {} executed successfully, returning: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "applicationServicePointcut()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("Method: {} threw exception: {}", joinPoint.getSignature().toShortString(), error.getMessage(), error);
    }
}
