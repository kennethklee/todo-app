package com.shashi.todo.dao;

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

import com.shashi.todo.model.Time;
import com.shashi.todo.model.Todo;

@Service
public class TodoDAOImpl implements TodoDAO {

	protected static Logger logger = LoggerFactory.getLogger(TodoDAOImpl.class);;

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
			logger.error("Search failed", e);
		}
		return null;
	}

	@Override
	public boolean create(Todo todo) {
		logger.debug("Adding a new user");

		try {
			todo.setTodoId(UUID.randomUUID().toString());
			todo.setCreatedTS(new Time());
			todo.setUpdatedTS(new Time());
			mongoTemplate.insert(todo, COLLECTION);
		} catch (Exception e) {
			logger.error("Create failed", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Todo todo) {
		try {
			todo.setUpdatedTS(new Time());
			Query query = new Query(Criteria.where("todoId").is(
					todo.getTodoId()));
			Update update = new Update();
			update.set("title", todo.getTitle());
			update.set("body", todo.getBody());
			update.set("done", todo.isDone());
			update.set("updatedTS", todo.getUpdatedTS());
			mongoTemplate.updateMulti(query, update, Todo.class);
		} catch (Exception e) {
			logger.error("Update failed", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Todo todo) {
		try {
			Query query = new Query(Criteria.where("todoId").is(
					todo.getTodoId()));
			mongoTemplate.remove(query, Todo.class);
		} catch (Exception e) {
			logger.error("Remove failed", e);
			return false;
		}
		return true;
	}

	@Override
	public List<Todo> findByName(String title) {
		try {
			return mongoTemplate.find(
					new Query(Criteria.where("title").is(title)), Todo.class);
		} catch (Exception e) {
			logger.error("Search all by name failed", e);
		}
		return null;
	}

	@Override
	public List<Todo> findAll() {
		try {
			return mongoTemplate.findAll(Todo.class, COLLECTION);
		} catch (Exception e) {
			logger.error("Search all failed", e);
		}
		return null;
	}

	@Override
	public boolean deleteAll() {
		try {
			mongoTemplate.dropCollection(Todo.class);
		} catch (Exception e) {
			logger.error("Delete all failed", e);
			return false;
		}
		return true;
	}

}
