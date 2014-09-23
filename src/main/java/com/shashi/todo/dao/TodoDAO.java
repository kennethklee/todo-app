package com.shashi.todo.dao;

import java.util.List;

import com.shashi.todo.model.Todo;

public interface TodoDAO {

	public Todo findById(String id);

	public void create(Todo person);

	public void update(Todo person);

	public void delete(Todo person);

	public List<Todo> findByName(String query);

	public List<Todo> findAll();
}
