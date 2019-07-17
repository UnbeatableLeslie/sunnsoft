package com.pengheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;

@ServletComponentScan
@EnableTransactionManagement
//自动扫描所有的Mapper类
@MapperScan(value="com.pengheng.mapper")
@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
public class StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

}
