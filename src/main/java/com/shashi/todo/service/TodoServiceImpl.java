package com.shashi.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.shashi.todo.model.Todo;

@Service
public class TodoServiceImpl implements TodoService {

	private static final String COLLECTION = "todo";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Todo findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Todo person) {
		// TODO Auto-generated method stub

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
