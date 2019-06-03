package com.pengheng.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	public static String getProperty(String key) {
		try {
			Resource resource = new ClassPathResource("config.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			return props.get(key).toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

//	public static void main(String[] args) {
//		System.out.println(PropertiesUtil.getProperty("gateWayAddress"));
//	}

	/**
	 * @param key
	 *            String
	 * @return String
	 */
	public static String getServiceName(String key) {
		try {
			Resource resource = new ClassPathResource("service.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			return props.get(key).toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取webmail.properties配置文件
	 * 
	 * @param key
	 * @return
	 */
	public static String getWebmailName(String key) {
		try {
			Resource resource = new ClassPathResource("webmail.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			return props.get(key).toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取webmail.properties配置文件
	 * 
	 * @param key
	 * @return
	 */
	public static String getMessageName(String key) {
		try {
			Resource resource = new ClassPathResource("message.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			return props.get(key).toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取application.properties配置文件
	 * 
	 * @param key
	 * @return
	 */
	public static String getApplicationName(String key) {
		try {
			Resource resource = new ClassPathResource("application.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			return props.get(key).toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}
