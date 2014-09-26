package com.shashi.todo.helper;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class LoggingResponseFilter implements ContainerResponseFilter {

	private static final Logger logger = Logger
			.getLogger(LoggingResponseFilter.class.getName());

	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		String method = requestContext.getMethod();

		logger.debug("Requesting " + method + " for path "
				+ requestContext.getUriInfo().getPath());
		Object entity = responseContext.getEntity();
		if (entity != null) {
			logger.debug("Response "
					+ new ObjectMapper().writerWithDefaultPrettyPrinter()
							.writeValueAsString(entity));
		}

	}

}