package com.pengheng.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pengheng.core.exception.GlobalExceptionHandler;
import com.pengheng.model.ResultVo;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler extends GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(UnauthorizedException.class)
	public ResultVo UnauthorizedExceptionHandler(UnauthorizedException ex) {
		logger.error(ex.toString());
		return new ResultVo("403", "用户未授权，无权限访问该功能");
	}
}
