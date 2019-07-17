package com.pengheng.config;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pengheng.core.exception.GlobalExceptionHandler;
import com.pengheng.model.ResultVo;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler extends GlobalExceptionHandler {
	
	private static Logger log = Logger.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(UnauthorizedException.class)
	public ResultVo UnauthorizedExceptionHandler(UnauthorizedException ex) {
		log.error(ex);
		return new ResultVo("403", "用户未授权，无权限访问该功能");
	}
}
