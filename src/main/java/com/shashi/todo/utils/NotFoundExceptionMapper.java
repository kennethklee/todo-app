package com.shashi.todo.utils;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.shashi.todo.model.ErrorInfo;

public class NotFoundExceptionMapper implements
		ExceptionMapper<NotFoundException> {

	public Response toResponse(NotFoundException ex) {
		return Response.status(ex.getResponse().getStatus())
				.entity(new ErrorInfo(ex)).type(MediaType.APPLICATION_JSON)
				.build();
	}

}