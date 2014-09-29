package com.shashi.todo.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shashi.todo.dao.TodoDAO;
import com.shashi.todo.helper.TodoConstants;
import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.Todo;
import com.shashi.todo.service.SearchService;
import com.shashi.todo.service.SmsService;
import com.shashi.todo.service.TodoCRUDService;

@Service
public class TodoCRUDServiceImpl implements TodoCRUDService {

	@Autowired
	private TodoDAO todoDao;

	@Autowired
	private SmsService smsService;

	@Autowired
	private SearchService searchService;

	@Override
	public Todo findById(String id) throws BusinessException {
		Todo todo = todoDao.findById(id);
		if (todo == null) {
			throw new BusinessException(
					Response.Status.NOT_FOUND.getStatusCode(),
					"No todo item found with id '" + id + "'",
					TodoConstants.NO_DATA_FOUND);
		}
		return todo;
	}

	@Override
	public void create(Todo todo) throws BusinessException {
		validateRequest(todo);
		if (todoDao.create(todo)) {
			searchService.create(todo);
			if (todo.isDone()) {
				smsService.sendMessage("Task '" + todo.getTitle()
						+ "' is marked as done");
			}
		} else {
			throw new BusinessException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					"Failed to add Todo", TodoConstants.TODO_CREATE_FAILED);
		}
	}

	@Override
	public void delete(String id) throws BusinessException {

		if (isExistingTodo(id)) {
			if (!todoDao.delete(id)) {
				throw new BusinessException(
						Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
						"Deletion failed for id '" + id + "'",
						TodoConstants.TODO_DELETION_FAILED);
			}
			searchService.delete(id);
		} else {
			String message = "No todo item found";
			if (StringUtils.isBlank(id)) {
				message = "No todo item found with id '" + id + "'";
			}
			throw new BusinessException(
					Response.Status.NOT_FOUND.getStatusCode(), message,
					TodoConstants.NO_DATA_FOUND);
		}
	}

	@Override
	public void deleteAll() throws BusinessException {
		if (!todoDao.deleteAll()) {
			throw new BusinessException(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
					"Deletion failed", TodoConstants.TODO_ALL_DELETION_FAILED);
		}
		searchService.deleteAll();
	}

	@Override
	public List<Todo> findByName(String query) throws BusinessException {
		if (StringUtils.isEmpty(query)) {
			throw new BusinessException(
					Response.Status.BAD_REQUEST.getStatusCode(),
					"Title is a mandatory field",
					TodoConstants.TODO_CREATE_REQ_INVALID);
		}
		List<Todo> todoList = searchService.search(query);
		if (todoList != null && todoList.size() > 0) {
			return todoList;
		} else {
			throw new BusinessException(
					Response.Status.NOT_FOUND.getStatusCode(),
					"No todo item found", TodoConstants.NO_DATA_FOUND);
		}

	}

	@Override
	public List<Todo> findAll() {
		return todoDao.findAll();
	}

	@Override
	public void update(Todo todo) throws BusinessException {
		boolean isSMSSent = false;
		Todo todoDB = todoDao.findById(todo.getTodoId());
		if (todoDB == null) {
			throw new BusinessException(
					Response.Status.NOT_FOUND.getStatusCode(), "Todo with id '"
							+ todo.getTodoId() + "' not found",
					TodoConstants.TODO_UPDATE_FAILED);
		} else {
			isSMSSent = todoDB.isDone();
			if (!todoDao.update(todo)) {
				throw new BusinessException(
						Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
						"Updation failed for id '" + todo.getTodoId() + "'",
						TodoConstants.TODO_UPDATE_FAILED);
			}
			todo.setCreatedTS(todoDB.getCreatedTS());
			searchService.update(todo);
			if (todo.isDone() && !isSMSSent) {
				smsService.sendMessage("Task '" + todo.getTitle()
						+ "' is marked as done");
			}
		}
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

	private boolean isExistingTodo(String id) {
		if (StringUtils.isBlank(id)) {
			return false;
		}
		if (todoDao.findById(id) != null) {
			return true;
		}
		return false;
	}
}
