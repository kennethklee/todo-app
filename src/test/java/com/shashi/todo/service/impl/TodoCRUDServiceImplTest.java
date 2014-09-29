package com.shashi.todo.service.impl;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.shashi.todo.dao.TodoDAO;
import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.Todo;
import com.shashi.todo.service.SearchService;
import com.shashi.todo.service.SmsService;

@RunWith(JMockit.class)
public class TodoCRUDServiceImplTest {

	@Injectable
	@Mocked
	private TodoDAO todoDao;

	@Injectable
	@Mocked
	private SmsService smsService;

	@Injectable
	@Mocked
	private SearchService searchService;

	@Tested
	private TodoCRUDServiceImpl todoCRUDService;

	@Test
	public void testFindById() throws BusinessException {
		new Expectations() {
			{
				todoDao.findById(anyString);
				result = new Todo();
			}
		};
		Todo todo = todoCRUDService.findById("1234");
		Assert.assertNotNull(todo);
	}

	@Test(expected = BusinessException.class)
	public void testFindByIdWithException() throws BusinessException {
		new Expectations() {
			{
				todoDao.findById(anyString);
				result = null;
			}
		};
		Todo todo = todoCRUDService.findById("1234");
	}

	@Test
	public void testCreate() throws BusinessException {
		final Todo todo = new Todo();
		todo.setDone(true);
		todo.setTitle("Title");
		new Expectations() {
			{
				todoDao.create(todo);
				result = true;
				searchService.create(todo);
				smsService.sendMessage(anyString);
			}
		};
		todoCRUDService.create(todo);
	}

	@Test
	public void testCreateNoSMS() throws BusinessException {
		final Todo todo = new Todo();
		todo.setDone(false);
		todo.setTitle("Title");
		new Expectations() {
			{
				todoDao.create(todo);
				result = true;
				searchService.create(todo);
			}
		};

		new Verifications() {
			{
				smsService.sendMessage(anyString);
				times = 0;
			}
		};
		todoCRUDService.create(todo);
	}

	@Test(expected = BusinessException.class)
	public void testCreateNoTitle() throws BusinessException {
		final Todo todo = new Todo();
		todoCRUDService.create(todo);
	}

	@Test(expected = BusinessException.class)
	public void testCreateNullTodo() throws BusinessException {
		todoCRUDService.create(null);
	}

	@Test(expected = BusinessException.class)
	public void testCreateWithId() throws BusinessException {
		final Todo todo = new Todo();
		todo.setDone(true);
		todo.setTitle("Title");
		todo.setTodoId("1234");
		todoCRUDService.create(todo);
	}

	@Test
	public void testDelete() throws BusinessException {
		final Todo todo = new Todo();
		todo.setDone(true);
		todo.setTitle("Title");
		todo.setTodoId("1234");
		new Expectations() {
			{
				todoDao.findById(anyString);
				result = new Todo();
				todoDao.delete(todo.getTodoId());
				result = true;
				searchService.delete(todo.getTodoId());
			}
		};
		todoCRUDService.delete(todo.getTodoId());
	}

	@Test(expected = BusinessException.class)
	public void testDeleteNullTodo() throws BusinessException {
		todoCRUDService.delete(null);
	}

	@Test(expected = BusinessException.class)
	public void testDeleteNullId() throws BusinessException {
		final Todo todo = new Todo();
		todo.setTodoId("");
		todoCRUDService.delete(todo.getTodoId());
	}

	@Test
	public void testDeleteAll() throws BusinessException {

		new Expectations() {
			{
				todoDao.deleteAll();
				result = true;
				searchService.deleteAll();
			}
		};
		todoCRUDService.deleteAll();
	}

	@Test
	public void testFindByName() throws BusinessException {
		final List<Todo> todoList = new ArrayList<Todo>();
		Todo todo = new Todo();
		todo.setDone(true);
		todo.setTitle("Title");
		todo.setTodoId("1234");
		todoList.add(todo);
		new Expectations() {
			{
				searchService.search(anyString);
				result = todoList;
			}
		};
		List<Todo> result = todoCRUDService.findByName("query");
		Assert.assertNotNull(result);
	}

	@Test
	public void testFindAll() {
		final List<Todo> todoList = new ArrayList<Todo>();
		Todo todo = new Todo();
		todo.setDone(true);
		todo.setTitle("Title");
		todo.setTodoId("1234");
		todoList.add(todo);
		new Expectations() {
			{
				todoDao.findAll();
				result = todoList;
			}
		};
		List<Todo> result = todoCRUDService.findAll();
		Assert.assertNotNull(result);
	}
}
