package com.pengheng.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Toolkits {

	private static final Logger logger = Logger.getLogger(Toolkits.class);
	private static final Map<String, String> applicationModules = new HashMap<String, String>();
	private static final Map<String, String> customServicesAndMethodsRule = new HashMap();
	private static final Map<Object, Object> onlineUsers = new HashMap();
	private static final Map<String, String> customDubboServices = new HashMap();

	public static final String defaultString(Object paramObject) {
		return defaultString(paramObject, "");
	}

	public static final String defaultString(Object paramObject, String paramString) {
		if ((paramObject != null) && (paramObject.equals("null")))
			paramObject = null;
		return (paramObject == null) || (((paramObject instanceof String)) && (paramObject.equals(""))) ? paramString
				: paramObject.toString();
	}

	public static final void useQueryPaging(Map<Object, Object> paramMap) {
		if ((!paramMap.containsKey("page")) && (paramMap.containsKey("meta.page"))) {
			paramMap.put("page", defaultString(paramMap.get("meta.page")));
			paramMap.remove("meta.page");
		}
		paramMap.put("querytype", "multi");
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
		if (paramObject != null)
			if (paramObject instanceof List || paramObject instanceof Collection || paramObject instanceof Object[]) {
				str = JSONArray.fromObject(paramObject).toString();
			} else {
				str = JSONObject.fromObject(paramObject).toString();
			}
		return str;
	}

	public static final String toJson(String paramString) {
		String str = "";
		if (!defaultString(paramString).equals(""))
			str = JSONObject.fromObject(paramString).toString();
		return str;
	}

}
