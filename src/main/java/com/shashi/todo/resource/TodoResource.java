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
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.Todo;
import com.shashi.todo.service.TodoCRUDService;

@Component
@Path("/todo")
public class TodoResource {

	static final Logger logger = LoggerFactory.getLogger(TodoResource.class);

	@Autowired
	private TodoCRUDService todoCRUDService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response create(Todo todo) throws BusinessException {
		todoCRUDService.create(todo);
		return Response.status(Response.Status.CREATED).entity(todo).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAlltodos() {
		List<Todo> todoList = todoCRUDService.findAll();
		if (todoList != null && todoList.size() > 0) {
			return Response.status(Response.Status.FOUND).entity(todoList)
					.build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByName(@PathParam("query") String query)
			throws BusinessException {
		List<Todo> todoList = todoCRUDService.findByName(query);
		if (todoList != null && todoList.size() > 0) {
			return Response.status(Response.Status.FOUND).entity(todoList)
					.build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findById(@PathParam("id") String id)
			throws BusinessException {
		Todo todo = todoCRUDService.findById(id);
		if (todo != null) {
			return Response.status(Response.Status.FOUND).entity(todo).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(@PathParam("id") String id, Todo todo)
			throws BusinessException {
		if (!StringUtils.isEmpty(id)) {
			todo.setTodoId(id);
		}
		todoCRUDService.update(todo);
		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") String id) throws BusinessException {
		todoCRUDService.delete(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteAll() throws BusinessException {
		todoCRUDService.deleteAll();
		return Response.status(Response.Status.NO_CONTENT).build();

	}
}
