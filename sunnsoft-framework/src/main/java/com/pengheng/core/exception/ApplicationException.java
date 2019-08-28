package com.pengheng.core.exception;

import com.pengheng.model.ReplyCode;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 5710541742740468778L;

	private ReplyCode code;

	public ApplicationException(ReplyCode code, String msg) {
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
	public ReplyCode getCode() {
		return code;
	}
}
