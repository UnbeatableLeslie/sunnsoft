package com.pengheng.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.PageHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public final class Toolkits {

	private static final Logger logger = LoggerFactory.getLogger(Toolkits.class);
	private static final Map<String, String> customServicesAndMethodsRule = new HashMap<String, String>();
	private static final Map<Object, Object> onlineUsers = new HashMap<Object, Object>();
	private static final Map<String, String> customDubboServices = new HashMap<String, String>();

	public static final String defaultString(Object paramObject) {
		return defaultString(paramObject, "");
	}

	public static final String defaultString(Object paramObject, String paramString) {
		if ((paramObject != null) && ("null".equals(paramObject))) {
			paramObject = null;
		}
		return (paramObject == null) || (((paramObject instanceof String)) && ("".equals(paramObject))) ? paramString
				: paramObject.toString();
	}

	public static final void useQueryPaging(Map<Object, Object> paramMap) {
		int pageNum = Integer.parseInt(Toolkits.defaultString(paramMap.get("pageNum"),"0"));
		int pageSize = Integer.parseInt(Toolkits.defaultString(paramMap.get("pageSize"),"20"));
		PageHelper.startPage(pageNum, pageSize);
	}

	public static final Map<String, String> getCustomServicesAndMethodsRule() {
		return customServicesAndMethodsRule;
	}

	public static final String getSystemPropertyValue(String paramString) {
		String str = "";
		try {
			str = StringUtils.defaultString(PropertiesUtil.getProperty(paramString));
		} catch (Exception localException) {
			logger.warn("加载配置项异常: " + localException.toString());
		}
		return str;
	}

	public static final String toJson(Object paramObject) {
		String str = "";
		if (paramObject != null) {
			if (paramObject instanceof List || paramObject instanceof Collection || paramObject instanceof Object[]) {
				str = JSONArray.fromObject(paramObject).toString();
			} else {
				str = JSONObject.fromObject(paramObject).toString();
			}
		}
		return str;
	}

	public static final String toJson(String paramString) {
		String str = "";
		if (!"".equals(defaultString(paramString))) {
			str = JSONObject.fromObject(paramString).toString();
		}
		return str;
	}

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
	}
	
	/**
	 * 获取 IP地址
	 * 使用 Nginx等反向代理软件， 则不能通过 request.getRemoteAddr()获取 IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
	 * X-Forwarded-For中第一个非 unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr() {
		return getIpAddr(getHttpServletRequest());
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

}
