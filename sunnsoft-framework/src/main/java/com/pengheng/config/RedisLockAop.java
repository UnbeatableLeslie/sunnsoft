package com.pengheng.config;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pengheng.core.exception.ApplicationException;
import com.pengheng.util.RedisUtils;

@Aspect
@Component
public class RedisLockAop {
	@Autowired
	private RedisUtils redisUtils;

	private static final Logger logger = LoggerFactory.getLogger(RedisLockAop.class);

	@Pointcut("@annotation(com.pengheng.core.annotation.RedisLock)")
	public void lockPointCut() {
	}

	@Before("lockPointCut()")
	public void doBefore(JoinPoint joinpoint) {
		MethodSignature signature = (MethodSignature) joinpoint.getSignature();
		String className = joinpoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		logger.info("请求" + className + "." + methodName);
	}

	@Around("lockPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		String key = "redis:lock:id:" + className + methodName;
		Object resultObject = null;

		Boolean res = false;
		String value = UUID.randomUUID().toString() + System.nanoTime();
		res = redisUtils.setNx(key, value, 5, TimeUnit.SECONDS);
		while (res) {
			if (res) {
				try {
					res = false;
					long starttime = System.currentTimeMillis();
					//处理数据库操作 删除数据
					resultObject = joinPoint.proceed();
					long endtime = System.currentTimeMillis();
					logger.info(" COST: " + (endtime - starttime) + "ms]");
				} catch (Exception e) {
					logger.error(e.toString());
					throw new ApplicationException(e);
				} finally {
					String redisValue = redisUtils.get(key);
					if (value.equals(redisValue)) {
						redisUtils.del(key);
					}
				}
			} else {
				Thread.sleep(1000);
			}
		}
		return resultObject;
	}
}
