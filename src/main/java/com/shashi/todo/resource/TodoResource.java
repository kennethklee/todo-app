package com.shashi.todo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shashi.todo.model.Todo;
import com.shashi.todo.service.TodoService;
import com.shashi.todo.utils.CommonUtils;

@Component
@Path("/todo")
public class TodoResource {

	static final Logger logger = LoggerFactory.getLogger(TodoResource.class);

	@Autowired
	private TodoService todoService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response create(Todo todo) {
		todoService.create(todo);
		return Response.status(201).entity(todo).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAlltodos() {
		return Response.status(200).entity(todoService.findAll()).build();
	}

	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByName(@PathParam("query") String query) {
		return Response.status(200).entity(todoService.findByName(query))
				.build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findById(@PathParam("id") String id) {
		Todo todo = todoService.findById(id);
		if (todo != null) {
			return Response.status(200).entity(todo).build();
		} else {
			return Response
					.status(404)
					.entity(CommonUtils.getExceptionReport("404",
							"The todo with the id " + id + " does not exist"))
					.build();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(@PathParam("id") String id, Todo todo) {
		if (todo.getTodoId() == null) {
			todo.setTodoId(id);
		}
		if (isTodoUpdated(todo)) {
			return Response.status(200).entity(todo).build();
		} else if (todoCreated(todo)) {
			return Response.status(200).entity(todo).build();
		} else {
			return Response
					.status(406)
					.entity(CommonUtils.getExceptionReport("406",
							"invalid request")).build();
		}
	}

	@DELETE
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") String id, Todo todo) {
		if (todo.getTodoId() == null) {
			todo.setTodoId(id);
		}
		if (todoService.delete(todo)) {
			return Response.status(204).build();
		}
		return Response
				.status(404)
				.entity("Todo with ID " + id
						+ " is not present in the database").build();
	}

	private boolean todoCreated(Todo todo) {
		if (todoService.findById(todo.getTodoId()) == null) {
			todoService.create(todo);
			return true;
		}
		return false;
	}

	private boolean isTodoUpdated(Todo todo) {
		return todoService.update(todo);
	}
}
