package com.pengheng.config.web;

import com.pengheng.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通过配置解决跨域问题
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String homePage = "/index.html";
        registry.addViewController("/").setViewName("redirect:" + homePage);
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
        	.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
        	.allowedHeaders("Authorization","Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
        	.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
            .allowCredentials(true).maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+configProperties.urlpath);
    }
}