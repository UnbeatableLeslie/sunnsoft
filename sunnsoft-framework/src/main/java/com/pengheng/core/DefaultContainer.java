package com.pengheng.core;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DefaultContainer implements ApplicationContextAware {
  private static final Logger logger = Logger.getLogger(DefaultContainer.class);
  
  private static ApplicationContext springContext = null;
  
  private static ServletContext servletContext = null;
  
  public static final ServletContext getServletContext() { return servletContext; }
  
  public static final void setServletContext(ServletContext paramServletContext) {
    servletContext = paramServletContext;
    if (isServletContextAvailable())
      logger.info("Container servlet context aware complete"); 
  }
  
  public static final boolean isSpringContextAvailable() { return (getSpringContext() != null); }
  
  public static final boolean isServletContextAvailable() { return (getServletContext() != null); }
  
  public void setApplicationContext(ApplicationContext paramApplicationContext) throws BeansException {
    springContext = paramApplicationContext;
    if (isSpringContextAvailable())
      logger.info("Spring application context aware complete"); 
  }
  
  public static final void setSpringContext(ApplicationContext paramApplicationContext) throws BeansException { springContext = paramApplicationContext; }
  
  public static final ApplicationContext getSpringContext() { return springContext; }
  
  public static final ApplicationContext getSpringContextByFileSystem(String paramString) { return new FileSystemXmlApplicationContext(paramString); }
  
  public static final ApplicationContext getSpringContextByFileSystem(String[] paramArrayOfString) { return new FileSystemXmlApplicationContext(paramArrayOfString); }
  
  public static final ApplicationContext getSpringContextByFileSystem(String[] paramArrayOfString, boolean paramBoolean) { return new FileSystemXmlApplicationContext(paramArrayOfString, paramBoolean); }
  
  public static final ApplicationContext getSpringContextByClassPath(String paramString) { return new ClassPathXmlApplicationContext(paramString); }
  
  public static final ApplicationContext getSpringContextByClassPath(String[] paramArrayOfString) { return new ClassPathXmlApplicationContext(paramArrayOfString); }
  
  public static final ApplicationContext getSpringContextByClassPath(String[] paramArrayOfString, boolean paramBoolean) { return new ClassPathXmlApplicationContext(paramArrayOfString, paramBoolean); }
  
  public static final WebApplicationContext getSpringContextByServletContext(ServletContext paramServletContext) { return WebApplicationContextUtils.getWebApplicationContext(paramServletContext); }
  
  public static final void destroy() {
    springContext = null;
    servletContext = null;
  }
}
