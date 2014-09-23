package com.shashi.todo.service;

import java.util.List;

import com.shashi.todo.model.Todo;

public interface TodoService {

	public Todo findById(String id);

	public void create(Todo person);

	public boolean update(Todo person);

	public boolean delete(Todo person);

	public List<Todo> findByName(String query);

	public List<Todo> findAll();
}
