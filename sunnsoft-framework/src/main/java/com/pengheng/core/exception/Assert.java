package com.pengheng.core.exception;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public final class Assert {
	public static final void error(String paramString) throws ApplicationException {
		throw new ApplicationException(paramString);
	}

	public static final void error(boolean paramBoolean, String paramString) throws ApplicationException {
		if (paramBoolean)
			throw new ApplicationException(paramString);
	}
	
	/**
	 * @description:断言给定参数不能为null,如果为null则抛出ApplicationException异常;
	 * @param code 错误代码
	 * @param message 异常信息
	 * @param object 待判定参数
	 */
	public static void notNull(int code , String message , Object object) {
		if(object == null) {
			throw new ApplicationException(code,message);
		}
	}
	
	
	/**
	 * @description:断言给定字符串不是空串,如果是空串则抛出ApplicationException异常;
	 * @param code 错误代码
	 * @param message 异常信息
	 * @param string 待判定字符串
	 */
	public static void notEmpty(int code,String message,String string) {
		if(StringUtils.isBlank(string)) {
			throw new ApplicationException(code,message);
		}
	}
	
	
	/**
	 * @description:断言给定集合不是空集合,如果是空集合则抛出ApplicationException异常;
	 * @param code 错误代码
	 * @param message 异常信息
	 * @param collection 待判定集合对象
	 */
	public static void notEmpty(int code , String message,Collection<?> collection)
	{
		if(collection == null || collection.isEmpty())
		{
			throw new ApplicationException(code,message);
		}
	}
	
	
	/**
	 * @description:断言给定参数数组全部不为null,如果有一个为null则抛出ApplicationException异常;
	 * @param code 错误代码
	 * @param message 异常信息
	 * @param objects 待判定参数集合
	 */
	public static void notNull(int code, String message, Object... objects)
	{
		for(Object object : objects)
		{
			if(object == null)
			{
				throw new ApplicationException(code,message);
			}
		}
	}
	
	/**
	 * @description:断言给定字符串参数数组全部不为空串,如果有一个为空串则抛出ApplicationException异常;
	 * @param code 错误代码
	 * @param message 异常信息
	 * @param strings 待判定字符串集合
	 */
	public static void notEmpty(int code ,String message, String... strings)
	{
		for(String string : strings)
		{
			if(StringUtils.isBlank(string))
			{
				throw new ApplicationException(code,message);
			}
		}
	}
	
	
	
	/**
	 * @description:断言给定数组对象不为空，且每个元数据都不为空,否则抛出ApplicationException异常;
	 * @param code 错误代码
	 * @param message 异常信息
	 * @param array 待判定数组
	 */
	public static void notEmpty(int code,String message,Object[] array)
	{
		if(array == null || array.length == 0)
		{
			throw new ApplicationException(code,message);
		}
		for(Object obj : array)
		{
			if(obj == null)
			{
				throw new ApplicationException(code,message);
			}
		}
	}
	
	/**
	 * @description:断言给定布尔型参数值为true,如果为null或false,则抛出ApplicationException异常;
	 * @param code 错误代码
	 * @param message 异常信息
	 * @param param 待判定Boolean对象
	 */
	public static void assertTrue(int code,String message,Boolean param)
	{
		if(param == null || !param)
		{
			throw new ApplicationException(code,message);
		}
	}
	/**
	 * @description:直接抛出异常，spring mvc 捕捉之后显示为json
	 * @param code 错误代码
	 * @param message 异常信息
	 */
	public static void exception(int code,String message) {
		throw new ApplicationException(code,message);
	}
	
	/**
	 * @description:设置参数默认值,如果给定参数为null,则设置其默认值,注意参数必须为数值型(Number类及其子类);
	 * @param param 待判定参数
	 * @param defaultValue 参数默认值
	 * @return 赋值之后的参数
	 * @author:liujun
	 * @date:2017年6月22日下午5:45:54
	 */
	public static Number ifNullSetDefault(Number param, Number defaultValue)
	{
		if(param == null)
		{
			param = defaultValue;
		}
		
		return param;
	}
}
