package com.pengheng.config;

import com.pengheng.util.Toolkits;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.pengheng.*.controller..*.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void before(JoinPoint joinpoint){
        Object [] args = joinpoint.getArgs();
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        logger.info("{}.{}:请求参数:{}",method.getDeclaringClass().getName(),method.getName(), Arrays.toString(joinpoint.getArgs()));
    }

    @AfterReturning(value = "logPointCut()",returning = "ret")
    public void after(JoinPoint joinpoint,Object ret){
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        logger.info("{}.{}:返回参数:{}",method.getDeclaringClass().getName(),method.getName(), Toolkits.toJson(ret));
    }
}
