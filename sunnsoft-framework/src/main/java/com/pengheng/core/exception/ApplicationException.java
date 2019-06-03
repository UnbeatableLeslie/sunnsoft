package com.pengheng.core.exception;

public class ApplicationException extends Exception {
	private static final long serialVersionUID = 5710541742740468778L;

	public ApplicationException() {
	}

	public ApplicationException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public ApplicationException(String paramString) {
		super(paramString);
	}

	public ApplicationException(Throwable paramThrowable) {
		super(paramThrowable);
	}
}
