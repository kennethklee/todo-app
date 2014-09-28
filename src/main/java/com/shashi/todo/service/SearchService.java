package com.shashi.todo.service;

import java.util.List;

import com.shashi.todo.model.Todo;

public interface SearchService {

	public void create(Todo todo);

	public void update(Todo todo);

	public List<Todo> search(String query);

	public void delete(Todo todo);

	public void deleteAll();

}