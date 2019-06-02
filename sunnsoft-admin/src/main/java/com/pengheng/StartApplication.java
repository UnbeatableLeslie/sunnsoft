package com.pengheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;

//@EnableTransactionManagement
@MapperScan(value="com.pengheng.mapper")
@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
public class StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

}
