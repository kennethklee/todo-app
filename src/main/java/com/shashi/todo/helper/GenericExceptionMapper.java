package com.shashi.todo.helper;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.shashi.todo.model.ErrorInfo;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	private static final int GENERIC_APP_ERROR_CODE = 1000;

	public Response toResponse(Throwable ex) {

		ErrorInfo errorMessage = new ErrorInfo();
		setHttpStatus(ex, errorMessage);
		errorMessage.setCode(GENERIC_APP_ERROR_CODE);
		errorMessage.setMessage(ex.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));

		return Response.status(errorMessage.getStatus()).entity(errorMessage)
				.type(MediaType.APPLICATION_JSON).build();
	}

	private void setHttpStatus(Throwable ex, ErrorInfo errorMessage) {
		if (ex instanceof WebApplicationException) {
			errorMessage.setStatus(((WebApplicationException) ex).getResponse()
					.getStatus());
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR
					.getStatusCode());
		}
	}
}