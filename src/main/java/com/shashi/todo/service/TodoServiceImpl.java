package com.shashi.todo.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.shashi.todo.model.Todo;

@Service
public class TodoServiceImpl implements TodoService {

	protected static Logger logger = LoggerFactory
			.getLogger(TodoServiceImpl.class);;

	private static final String COLLECTION = "todo";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Todo findById(String id) {
		// TODO Auto-generated method stub
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
	public void update(Todo person) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Todo person) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Todo> findByName(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Todo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
