package com.pengheng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WelcomeConfig implements WebMvcConfigurer {
    
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String homePage = "/index.html";
        registry.addViewController("/").setViewName("redirect:" + homePage);
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}