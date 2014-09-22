package com.shashi.todo.resource;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class TodoApplicationConfig extends ResourceConfig {

	public TodoApplicationConfig() {
		register(RequestContextFilter.class);
		register(TodoResource.class);
		register(JacksonFeature.class);
	}

}
