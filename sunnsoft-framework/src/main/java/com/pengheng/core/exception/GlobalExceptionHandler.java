package com.pengheng.core.exception;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoFailure;
import com.pengheng.util.Toolkits;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";
	private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);

	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	public String runtimeExceptionHandler(RuntimeException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 空指针异常
	@ExceptionHandler(NullPointerException.class)
	public String nullPointerExceptionHandler(NullPointerException ex) {
		System.err.println("NullPointerException:");
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 类型转换异常
	@ExceptionHandler(ClassCastException.class)
	public String classCastExceptionHandler(ClassCastException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// IO异常
	@ExceptionHandler(IOException.class)
	public String iOExceptionHandler(IOException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 未知方法异常
	@ExceptionHandler(NoSuchMethodException.class)
	public String noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public String indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 400错误
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public String requestNotReadable(HttpMessageNotReadableException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 400错误
	@ExceptionHandler({ TypeMismatchException.class })
	public String requestTypeMismatch(TypeMismatchException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 400错误
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public String requestMissingServletRequest(MissingServletRequestParameterException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 405错误
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public String request405(HttpRequestMethodNotSupportedException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 406错误
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	public String request406(HttpMediaTypeNotAcceptableException ex) {
		log.error(resultFormat(ex));
		System.out.println("406...");
		return resultFormat(ex);
	}

	// 500错误
	@ExceptionHandler({ ConversionNotSupportedException.class, HttpMessageNotWritableException.class })
	public String server500(RuntimeException ex) {
		log.error(resultFormat(ex));
		System.out.println("500...");
		return resultFormat(ex);
	}

	// 栈溢出
	@ExceptionHandler({ StackOverflowError.class })
	public String requestStackOverflow(StackOverflowError ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 除数不能为0
	@ExceptionHandler({ ArithmeticException.class })
	public String arithmeticException(ArithmeticException ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	// 其他错误
	@ExceptionHandler({ Exception.class })
	public String exception(Exception ex) {
		log.error(resultFormat(ex));
		return resultFormat(ex);
	}

	private <T extends Throwable> String resultFormat(T ex) {
		log.error(Toolkits.toJson(new ResultVoFailure(ex.getMessage())));
		ex.printStackTrace();
		log.error(String.format(logExceptionFormat, ex.getMessage()));
		return Toolkits.toJson(new ResultVoFailure(ex.getMessage()));
	}

}
