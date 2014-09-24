package com.shashi.todo.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.shashi.todo.model.ErrorInfo;
import com.shashi.todo.model.BusinessException;

public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	public Response toResponse(BusinessException ex) {
		return Response.status(ex.getCode()).entity(new ErrorInfo(ex))
				.type(MediaType.APPLICATION_JSON).build();
	}

}