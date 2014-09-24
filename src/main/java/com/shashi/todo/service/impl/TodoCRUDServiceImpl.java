package com.shashi.todo.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shashi.todo.dao.TodoDAO;
import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.Todo;
import com.shashi.todo.service.TodoCRUDService;
import com.shashi.todo.utils.TodoConstants;

@Service
public class TodoCRUDServiceImpl implements TodoCRUDService {

	@Autowired
	private TodoDAO todoDao;

	@Override
	public Todo findById(String id) throws BusinessException {
		Todo todo = todoDao.findById(id);
		if (todo == null) {
			throw new BusinessException(
					Response.Status.NOT_FOUND.getStatusCode(),
					"No todo item found for given id",
					TodoConstants.NO_DATA_FOUND);
		}
		return todo;
	}

	@Override
	public boolean create(Todo todo) throws BusinessException {
		validateRequest(todo);
		return todoDao.create(todo);
	}

	private boolean validateRequest(Todo todo) throws BusinessException {
		if (todo == null) {
			throw new BusinessException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					"Title is a mandatory field",
					TodoConstants.TODO_CREATE_REQ_INVALID);
		}

		if (!StringUtils.isEmpty(todo.getTodoId())) {
			throw new BusinessException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					"Id should be empty for creating new Todo item",
					TodoConstants.TODO_CREATE_REQ_INVALID);
		}

		if (todo != null && StringUtils.isEmpty(todo.getTitle())) {
			throw new BusinessException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					"Title is a mandatory field",
					TodoConstants.TODO_CREATE_REQ_INVALID);
		}
		return true;
	}

	@Override
	public boolean update(Todo todo) throws BusinessException {
		if (isExistingTodo(todo)) {
			return todoDao.update(todo);
		}
		return create(todo);
	}

	private boolean isExistingTodo(Todo todo) throws BusinessException {
		if (todoDao.findById(todo.getTodoId()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Todo todo) throws BusinessException {
		if (isExistingTodo(todo)) {
			return todoDao.delete(todo);
		}
		return false;
	}

	@Override
	public List<Todo> findByName(String query) throws BusinessException {
		if (StringUtils.isEmpty(query)) {
			throw new BusinessException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					"Title is a mandatory field",
					TodoConstants.TODO_CREATE_REQ_INVALID);
		}
		return todoDao.findByName(query);
	}

	@Override
	public List<Todo> findAll() {
		return todoDao.findAll();
	}

}
