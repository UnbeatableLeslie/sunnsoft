package com.pengheng.config.database;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
//自动扫描所有的Mapper类
@MapperScan(value="com.pengheng.**.mapper")
public class MyBatisConfig {

	public ConfigurationCustomizer configurationCustomizer() {
		return new ConfigurationCustomizer() {

			@Override
			public void customize(org.apache.ibatis.session.Configuration configuration) {
				configuration.setMapUnderscoreToCamelCase(true);
			}
			
		};
	}
}
