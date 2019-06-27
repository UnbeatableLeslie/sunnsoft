package com.pengheng.config;

import com.pengheng.core.interceptor.CommonIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private CommonIntercepter commonIntercepter;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String homePage = "/index.html";
        registry.addViewController("/").setViewName("redirect:" + homePage);
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonIntercepter).addPathPatterns("/**").excludePathPatterns("/login", "/register");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
        	.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
        	.allowedHeaders("Authorization","Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
        	.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
            .allowCredentials(true).maxAge(3600);
    }
}