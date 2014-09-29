package com.shashi.todo.dao;

import java.util.List;

import com.shashi.todo.model.Todo;

public interface TodoDAO {

	public Todo findById(String id);

	public boolean create(Todo todo);

	public boolean update(Todo todo);

	public List<Todo> findByName(String query);

	public List<Todo> findAll();

	public boolean deleteAll();

	public boolean delete(String id);
}
