package com.shashi.todo.resource;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.shashi.todo.helper.BusinessExceptionMapper;
import com.shashi.todo.helper.GenericExceptionMapper;
import com.shashi.todo.helper.LoggingResponseFilter;
import com.shashi.todo.helper.NotFoundExceptionMapper;

public class TodoApplicationConfig extends ResourceConfig {

	public TodoApplicationConfig() {
		register(RequestContextFilter.class);
		register(TodoResource.class);
		register(RegisterNumber.class);
		register(JacksonFeature.class);
		register(LoggingResponseFilter.class);
		register(GenericExceptionMapper.class);
		register(NotFoundExceptionMapper.class);
		register(BusinessExceptionMapper.class);
	}
}
