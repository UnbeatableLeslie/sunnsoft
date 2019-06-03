package com.pengheng.core.exception;

public final class Assert {
	public static final void error(String paramString) throws ApplicationException {
		throw new ApplicationException(paramString);
	}

	public static final void error(boolean paramBoolean, String paramString) throws ApplicationException {
		if (paramBoolean)
			throw new ApplicationException(paramString);
	}
}
