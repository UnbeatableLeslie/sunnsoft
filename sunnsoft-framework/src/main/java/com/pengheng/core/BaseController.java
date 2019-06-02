package com.pengheng.core;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pengheng.util.Toolkits;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseController {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger logger = Logger.getLogger(BaseController.class);

	@InitBinder
	public void initBinder(ServletRequestDataBinder paramServletRequestDataBinder) 
	{
		paramServletRequestDataBinder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

	@ModelAttribute("parameterMap")
	public Map<Object, Object> prepend(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) 
	{
		HttpSession localHttpSession = paramHttpServletRequest.getSession();
		logger.debug("current session: " + localHttpSession.getId());
		Map localMap = convertParameterMap(paramHttpServletRequest, paramHttpServletResponse);
		return localMap;
	}

	protected Object convertJson2Object(String paramString, Class<?> paramClass)
			throws JsonParseException, JsonMappingException, IOException 
	{
		return objectMapper.readValue(paramString, paramClass);
	}

	private Map<Object, Object> convertParameterMap(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) 
	{
		Object localObject = new HashMap();
		try 
		{
			String str = Toolkits.defaultString(paramHttpServletRequest.getParameter("jsonData"));
			if (!str.equals(""))
				localObject = (Map) convertJSON2Object(str, Map.class);
			((Map) localObject).putAll(fillParameterMap(paramHttpServletRequest, paramHttpServletResponse));
		} 
		catch (Exception localException) 
		{
			logger.error("转换请求参数失败", localException);
		}
		return (Map) localObject;
	}

	private Map<Object, Object> fillParameterMap(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) 
	{
		HashMap localHashMap = new HashMap();
		Map localMap = paramHttpServletRequest.getParameterMap();
		Iterator localIterator = localMap.entrySet().iterator();
		while (localIterator.hasNext()) 
		{
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			String str1 = Toolkits.defaultString(localEntry.getKey());
			String str2 = Toolkits.defaultString(paramHttpServletRequest.getParameter(str1));
			if ((!str2.equals("")) && (!str1.equalsIgnoreCase("jsonData")) && (!str1.equalsIgnoreCase("callService")))
				localHashMap.put(str1, str2);
		}
		Map<String, String> userMap = (Map<String, String>) paramHttpServletRequest.getSession()
				.getAttribute("UserInfo");
		if (userMap != null && userMap.size() > 0) 
		{
			localHashMap.put("orgId", userMap.get("orgId"));
			// localHashMap.put("orgName",userMap.get("orgName"));
			localHashMap.put("workId", userMap.get("workId"));
		}
		return localHashMap;
	}

	protected String convertObject2Json(Object paramObject)
			throws JsonGenerationException, JsonMappingException, IOException {
		String str = null;
		StringWriter localStringWriter = new StringWriter();
		try 
		{
			objectMapper.writeValue(localStringWriter, paramObject);
			localStringWriter.flush();
			str = localStringWriter.toString();
		} 
		finally 
		{
			IOUtils.closeQuietly(localStringWriter);
		}
		return str;
	}

	protected Object convertJSON2Object(String paramString, Class<?> paramClass) throws Exception 
	{
		return objectMapper.readValue(paramString, paramClass);
	}

	protected Map<Object, Object> getParameterMap(Model paramModel) 
	{
		return (Map) paramModel.asMap().get("parameterMap");
	}

}
