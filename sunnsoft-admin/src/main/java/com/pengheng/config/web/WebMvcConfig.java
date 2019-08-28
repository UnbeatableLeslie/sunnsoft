package com.pengheng.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.pengheng.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;

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

    @Bean
    public ServletRegistrationBean servletRegistrationBean() throws ServletException{
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(),"/images/kaptcha.jpg");
        servlet.addInitParameter("kaptcha.border", "no");
        servlet.addInitParameter("kaptcha.textproducer.font.color", "blue");
        servlet.addInitParameter("kaptcha.image.width", "125");
        servlet.addInitParameter("kaptcha.image.height", "60");
        servlet.addInitParameter("kaptcha.textproducer.char.string", "0123456789");
        servlet.addInitParameter("kaptcha.textproducer.char.space", "4");
        servlet.addInitParameter("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        servlet.addInitParameter("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        servlet.addInitParameter("kaptcha.textproducer.char.length", "4");
        return servlet;
    }
}