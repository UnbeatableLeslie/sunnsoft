package com.pengheng.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class ConfigLoader {

    private YamlPropertiesFactoryBean yamlPropertiesFactoryBean;
    private AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();

    private final static ConfigLoader configLoader = new ConfigLoader();

    private ConfigLoader() {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("application.yml"));
        this.yamlPropertiesFactoryBean = yamlPropertiesFactoryBean;
    }

    public static ConfigLoader getInstance() {
        return configLoader;
    }

    public String getValue(String key) {
        String value = yamlPropertiesFactoryBean.getObject().getProperty(key);
        return parseValue(value);
    }


    private String parseValue(String expression) {
        expression = expression.trim();
        if (expression.startsWith("${")) {
            String temp = expression.substring(2, expression.lastIndexOf("}"));
            int spIndex = temp.indexOf(":");
            if (spIndex != -1) {
                String exp = temp.substring(0, spIndex);
                String defaultValue = temp.substring(spIndex + 1);


                String propertyValue = configApplicationContext.getEnvironment().getProperty(exp);
                if (StringUtils.isBlank(propertyValue)) {
                    return defaultValue;
                }
                return propertyValue;
            } else {
                return configApplicationContext.getEnvironment().getProperty(temp);
            }
        } else {
            return expression;
        }
    }

}
