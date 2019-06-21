package com.pengheng.core;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DefaultContainer implements ApplicationContextAware {
	private static final Logger logger = Logger.getLogger(DefaultContainer.class);

	private static ApplicationContext applicationContext;   

    @Override

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    	this.applicationContext = applicationContext;

        logger.info("ApplicationContext配置成功,applicationContext对象："+applicationContext);

    }

    public static ApplicationContext getApplicationContext() {

        return applicationContext;

    }

    public static Object getBean(String name) {

        return getApplicationContext().getBean(name);

    }

    public static <T> T getBean(Class<T> clazz) {

        return getApplicationContext().getBean(clazz);

    }

    public static <T> T getBean(String name,Class<T> clazz) {

        return getApplicationContext().getBean(name,clazz);

    }
}
