package com.pengheng.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public final class Toolkits {

	private static final Logger logger = LoggerFactory.getLogger(Toolkits.class);
	private static final Map<String, String> customServicesAndMethodsRule = new HashMap<String, String>();
	private static final Map<Object, Object> onlineUsers = new HashMap<Object, Object>();
	private static final Map<String, String> customDubboServices = new HashMap<String, String>();

	public static String defaultString(Object paramObject) {
		return defaultString(paramObject, "");
	}

	public static String defaultString(Object paramObject, String paramString) {
		if (("null".equals(paramObject))) {
			paramObject = null;
		}
		return (paramObject == null) || (((paramObject instanceof String)) && ("".equals(paramObject))) ? paramString
				: paramObject.toString();
	}

	public static Map<Object,Object> getPageMap(){
		Map<Object,Object> pageMap = new HashMap<>();
        HttpServletRequest httpServletRequest = Toolkits.getHttpServletRequest();
        httpServletRequest.setAttribute("usePageHelper","true");

        int pageNum;
		int pageSize;
		try {
			pageNum = Integer.parseInt(httpServletRequest.getParameter("pageNum"));
		} catch (Exception e) {
			pageNum = 1;
		}
		try {
			pageSize = Integer.parseInt(httpServletRequest.getParameter("pageSize"));
		} catch (Exception e) {
			pageSize = 20;
		}
		pageMap.put("pageNum",pageNum);
		pageMap.put("pageSize",pageSize);
		return pageMap;
	}

	public static void useQueryPaging() {
		Map<Object, Object> pageMap = getPageMap();
		Object pageNum = pageMap.get("pageNum");
		Object pageSize = pageMap.get("pageSize");
		PageHelper.startPage(Integer.parseInt(Toolkits.defaultString(pageNum,"1")), Integer.parseInt(Toolkits.defaultString(pageSize,"20")));
	}

	public static IPage usePlusQueryPage() {
		Map<Object, Object> pageMap = getPageMap();
		long pageNum = Long.parseLong(Toolkits.defaultString(pageMap.get("pageNum")));
		long pageSize = Long.parseLong(Toolkits.defaultString(pageMap.get("pageSize")));
		return new Page(pageNum,pageSize);

	}

	public static Map<String, String> getCustomServicesAndMethodsRule() {
		return customServicesAndMethodsRule;
	}

	public static String getSystemPropertyValue(String paramString) {
		String str = "";
		try {
			str = StringUtils.defaultString(PropertiesUtil.getProperty(paramString));
		} catch (Exception localException) {
			logger.warn("加载配置项异常: " + localException.toString());
		}
		return str;
	}

	public static String toJson(Object paramObject) {
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

	public static String toJson(String paramString) {
		String str = "";
		if (!"".equals(defaultString(paramString))) {
			str = JSONObject.fromObject(paramString).toString();
		}
		return str;
	}

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
	}

	public static HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
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

	public static boolean isAjax(HttpServletRequest req){
		//判断是否为ajax请求，默认不是
		return "XMLHttpRequest".equals(req.getHeader("x-requested-with"));
	}

}
