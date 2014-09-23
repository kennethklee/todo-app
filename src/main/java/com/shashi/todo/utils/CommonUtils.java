package com.shashi.todo.utils;

import com.shashi.todo.model.ExceptionReport;

public class CommonUtils {

	private CommonUtils() {
		// closed
	}

	public static ExceptionReport getExceptionReport(String code, String message) {

		return new ExceptionReport("Data retrieval", message, code, null);

	}

}
