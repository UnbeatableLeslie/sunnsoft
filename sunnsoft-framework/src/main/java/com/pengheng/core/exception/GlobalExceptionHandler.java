package com.pengheng.core.exception;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoFailure;
import com.pengheng.util.Toolkits;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ApplicationException.class)
	public ResultVo ApplicationExceptionHandler(ApplicationException ex) {
		return new ResultVo(Toolkits.defaultString(ex.getCode()),ex.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResultVo UnauthorizedExceptionHandler(UnauthorizedException ex) {
		log.error(ex);
		return new ResultVo("403", "用户未授权，无权限访问该功能");
	}

	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	public ResultVo runtimeExceptionHandler(RuntimeException ex) {
		log.error(ex);
		return new ResultVoFailure("系统异常");
	}

	// 其他错误
	@ExceptionHandler({ Exception.class })
	public ResultVo exception(Exception ex) {
		log.error(ex);
		return new ResultVoFailure("系统异常");
	}

}
