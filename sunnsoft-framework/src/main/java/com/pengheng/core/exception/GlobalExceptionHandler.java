package com.pengheng.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoFailure;
import com.pengheng.util.Toolkits;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ApplicationException.class)
	public ResultVo ApplicationExceptionHandler(ApplicationException ex) {
		logger.error(ex.getMessage(),ex);
		return new ResultVo(Toolkits.defaultString(ex.getCode()),ex.getMessage());
	}

	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	public ResultVo runtimeExceptionHandler(RuntimeException ex) {
		logger.error(ex.getMessage(),ex);
		return new ResultVoFailure("系统异常");
	}

	// 其他错误
	@ExceptionHandler({ Exception.class })
	public ResultVo exception(Exception ex) {
		logger.error(ex.getMessage(),ex);
		return new ResultVoFailure("系统异常");
	}

}
