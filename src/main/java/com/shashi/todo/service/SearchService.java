package com.shashi.todo.service;

import java.util.List;

import com.shashi.todo.model.Todo;

public interface SearchService {

	public void createIndex(Todo todo);

	public void updateIndex(Todo todo);

	public void deleteIndex(Todo todo);

	List<Todo> search(String query);

}