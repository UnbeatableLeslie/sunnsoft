package com.pengheng.core.exception;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 5710541742740468778L;

	private int code;

	public ApplicationException(int code, String msg) {
		super(msg);
		this.code = code;

	}

	public ApplicationException() {
	}

	public ApplicationException(String paramString) {
		super(paramString);
	}

	public ApplicationException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	/**
	 * @return 错误码
	 */
	public int getCode() {
		return code;
	}
}
