package com.shashi.todo.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shashi.todo.model.Todo;
import com.shashi.todo.service.SmsService;
import com.shashi.todo.service.TodoService;
import com.twilio.sdk.TwilioRestException;

@Service
public class TodoServiceImpl implements TodoService {

	protected static Logger logger = LoggerFactory
			.getLogger(TodoServiceImpl.class);;

	private static final String COLLECTION = "todo";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SmsService smsService;

	@Override
	public Todo findById(String id) {
		logger.debug("find by id ");
		try {
			return mongoTemplate.findOne(
					new Query(Criteria.where("todoId").is(id)), Todo.class);
		} catch (Exception e) {
			logger.error(
					"An error has occurred while trying to find todo by id", e);
		}
		return null;
	}

	@Override
	public void create(Todo todo) {
		logger.debug("Adding a new user");

		try {
			todo.setTodoId(UUID.randomUUID().toString());
			mongoTemplate.insert(todo, COLLECTION);
		} catch (Exception e) {
			logger.error("An error has occurred while trying to add new user",
					e);
		}

	}

	@Override
	public boolean update(Todo todo) {
		logger.debug("Adding a new user");
		try {
			Query query = new Query(Criteria.where("todoid").is(
					todo.getTodoId()));
			Update update = new Update();
			update.set("done", todo.isDone());
			mongoTemplate.updateFirst(query, update, Todo.class);
			if (todo.isDone()) {
				smsService.sendMessage("+13157068730", "\"" + todo.getBody()
						+ "\" has been marked done");
			}
			return true;
		} catch (TwilioRestException ex) {
			logger.error("An error has occurred while trying to update todo",
					ex);
			return true;
		} catch (Exception e) {
			logger.error("An error has occurred while trying to remove todo", e);
			return false;
		}
	}

	@Override
	public boolean delete(Todo todo) {
		logger.debug("Removing a todo");
		try {
			Query query = new Query(Criteria.where("todoId").is(
					todo.getTodoId()));
			mongoTemplate.remove(query, Todo.class);
			return true;
		} catch (Exception e) {
			logger.error("An error has occurred while trying to remove todo", e);
			return false;
		}

	}

	@Override
	public List<Todo> findByName(String title) {
		logger.debug("find all todos");
		try {
			return mongoTemplate.find(
					new Query(Criteria.where("title").is(title)), Todo.class);
		} catch (Exception e) {
			logger.error(
					"An error has occurred while trying find all todos by name",
					e);
		}
		return null;
	}

	@Override
	public List<Todo> findAll() {
		logger.debug("find all todos");

		try {
			return mongoTemplate.findAll(Todo.class, COLLECTION);
		} catch (Exception e) {
			logger.error("An error has occurred while trying find all todos", e);
		}
		return null;
	}

}
