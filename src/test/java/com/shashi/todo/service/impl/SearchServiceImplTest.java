package com.shashi.todo.service.impl;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.shashi.todo.model.Todo;

@RunWith(JMockit.class)
public class SearchServiceImplTest {

	@Injectable
	@Mocked
	private JestClient jestClient;

	@Tested
	private SearchServiceImpl subject;

	@Test
	public void testCreate() throws Exception {
		Todo todo = new Todo();
		final JestResult jestResult = new JestResult(new Gson());
		jestResult.setSucceeded(false);
		new Expectations() {
			{
				jestClient.execute((IndicesExists) any);
				result = jestResult;
				jestClient.execute((CreateIndex) any);
				jestClient.execute((Index) any);
			}
		};

		subject.create(todo);
	}

	@Test
	public void testCreateWhenIndexExist() throws Exception {
		Todo todo = new Todo();
		final JestResult jestResult = new JestResult(new Gson());
		jestResult.setSucceeded(true);
		new Expectations() {
			{
				jestClient.execute((IndicesExists) any);
				result = jestResult;
				jestClient.execute((Index) any);
			}
		};
		subject.create(todo);
	}

	@Test
	public void testSearch() throws Exception {
		String query = "search";
		final JestResult jestResult = new JestResult(new Gson());
		Gson gson = new Gson();
		List<Todo> todoList = new ArrayList<Todo>();
		Todo todo = new Todo();
		todo.setTodoId("12345");
		todo.setBody("description");
		todo.setDone(false);
		todo.setTitle("Title");
		todoList.add(todo);
		String json = gson.toJson(todoList, new TypeToken<List<Todo>>() {
		}.getType());
		JsonArray newObj = new JsonParser().parse(json).getAsJsonArray();

		jestResult.setJsonString(json);
		jestResult.setSucceeded(true);
		new Expectations() {
			{
				jestClient.execute((Search) any);
				result = jestResult;
			}
		};
		List<Todo> result = subject.search(query);
		Assert.assertNotNull(result);
	}

	@Test
	public void testSearchWithException() throws Exception {
		String query = "search";
		new Expectations() {
			{
				jestClient.execute((Search) any);
				result = new Exception();
			}
		};
		List<Todo> result = subject.search(query);
		Assert.assertNull(result);
	}

	@Test
	public void testUpdateIndex() throws Exception {
		Todo todo = new Todo();
		final JestResult jestResult = new JestResult(new Gson());
		jestResult.setSucceeded(false);
		new Expectations() {
			{
				jestClient.execute(((Delete) any));
				jestClient.execute((IndicesExists) any);
				result = jestResult;
				jestClient.execute((CreateIndex) any);
				jestClient.execute((Index) any);
			}
		};
		subject.update(todo);
	}

	@Test
	public void testDeleteIndex() throws Exception {
		Todo todo = new Todo();
		todo.setTodoId("1234");
		final JestResult jestResult = new JestResult(new Gson());
		jestResult.setSucceeded(false);
		new Expectations() {
			{
				jestClient.execute(((Delete) any));
			}
		};
		subject.delete(todo.getTodoId());
	}

}
