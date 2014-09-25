package com.shashi.todo.helper;

import com.shashi.todo.model.BusinessException;

public class CommonUtils {

	private CommonUtils() {
		// closed
	}

	public static BusinessException getExceptionReport(int status, int code,
			String message) {
		return new BusinessException(status, message, code);
	}

}
