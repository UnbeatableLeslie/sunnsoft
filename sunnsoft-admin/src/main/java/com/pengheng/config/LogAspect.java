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
import org.springframework.ui.Model;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.pengheng.*.controller..*.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void before(JoinPoint joinpoint) {
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        logger.info("{}.{}:请求参数:{}", method.getDeclaringClass().getName(), method.getName(), Toolkits.toJson(excludeArray(joinpoint.getArgs())));
    }

    @AfterReturning(value = "logPointCut()", returning = "ret")
    public void after(JoinPoint joinpoint, Object ret) {
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();
        logger.info("{}.{}:返回参数:{}", method.getDeclaringClass().getName(), method.getName(), Toolkits.toJson(ret));
    }


    /**
     * 排除 Request  Response Model 参数 避免转换json异常
     */
    private List excludeArray(Object[] args) {
        List<Object> arr = new ArrayList<>();
        for (Object arg1 : args) {
            if (arg1 instanceof ServletRequest) {
                ServletRequest arg = (ServletRequest) arg1;
                arr.add(Toolkits.toJson(arg.getParameterMap()));
            } else if (arg1 instanceof ServletResponse) {

            } else if (arg1 instanceof Model) {
                Model arg = (Model) arg1;
                arr.add(Toolkits.toJson(Toolkits.toJson(arg.asMap().get("parameterMap"))));
            } else {
                arr.add(arg1);
            }
        }
        return arr;
    }
}