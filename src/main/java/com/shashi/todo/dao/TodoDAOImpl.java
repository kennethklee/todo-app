package com.shashi.todo.dao;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.shashi.todo.model.Todo;

@Service
public class TodoDAOImpl implements TodoDAO {

	protected static Logger logger = LoggerFactory
			.getLogger(TodoDAOImpl.class);;

	private static final String COLLECTION = "todo";

	@Autowired
	private MongoTemplate mongoTemplate;

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
	public void update(Todo todo) {
		logger.debug("Adding a new user");
		try {
			mongoTemplate.save(todo);
		} catch (Exception e) {
			logger.error("An error has occurred while trying to remove todo", e);
		}

	}

	@Override
	public void delete(Todo todo) {
		logger.debug("Adding a new user");

		try {
			mongoTemplate.remove(todo);
		} catch (Exception e) {
			logger.error("An error has occurred while trying to remove todo", e);
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
