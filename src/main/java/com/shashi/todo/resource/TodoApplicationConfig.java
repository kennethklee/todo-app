package com.shashi.todo.resource;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.shashi.todo.utils.BusinessExceptionMapper;
import com.shashi.todo.utils.GenericExceptionMapper;
import com.shashi.todo.utils.LoggingResponseFilter;
import com.shashi.todo.utils.NotFoundExceptionMapper;

public class TodoApplicationConfig extends ResourceConfig {

	public TodoApplicationConfig() {
		register(RequestContextFilter.class);
		register(TodoResource.class);
		register(JacksonFeature.class);
		register(LoggingResponseFilter.class);
		register(GenericExceptionMapper.class);
		register(NotFoundExceptionMapper.class);
		register(BusinessExceptionMapper.class);
	}

}
