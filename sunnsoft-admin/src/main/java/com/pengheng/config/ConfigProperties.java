package com.pengheng.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:config.properties")
public class ConfigProperties {

    @Value("${images.upload.path}")
    private String imagesUploadPath;
}
