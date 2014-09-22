package com.shashi.todo.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shashi.todo.model.Time;
import com.shashi.todo.model.Todo;
import com.shashi.todo.service.TodoService;

@Component
@Path("/todo")
public class TodoResource {

	static final Logger logger = LoggerFactory.getLogger(TodoResource.class);

	@Autowired
	private TodoService todoService;

	@GET
	@Produces("text/plain")
	@Path("/hello")
	public String getIt() {
		return "Hi there!";
	}

	@GET
	@Path("/time")
	@Produces("application/json")
	public Time get() {
		return new Time();
	}

	@GET
	// @Produces({ "application/json"})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Todo> findAlltodos() {
		List<Todo> results = todoService.findAll();
		return results;
	}

	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Todo> findByName(@PathParam("query") String query) {
		return todoService.findByName(query);
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Todo findById(@PathParam("id") String id) {
		return todoService.findById(id);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public boolean create(Todo todo) {
		todoService.create(todo);
		return true;
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Todo update(Todo todo) {
		todoService.update(todo);
		return todo;
	}

	@DELETE
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Todo delete(Todo todo) {
		todoService.delete(todo);
		return todo;
	}
}
