package com.shashi.todo.service;

import java.util.List;

import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.Todo;

public interface TodoCRUDService {

	public Todo findById(String id) throws BusinessException;

	public void create(Todo todo) throws BusinessException;

	public void update(Todo todo) throws BusinessException;

	public void delete(Todo todo) throws BusinessException;

	public List<Todo> findByName(String query) throws BusinessException;

	public List<Todo> findAll();

	public void deleteAll() throws BusinessException;

}
