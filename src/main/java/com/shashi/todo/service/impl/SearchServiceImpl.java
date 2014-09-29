package com.shashi.todo.service.impl;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shashi.todo.model.Todo;
import com.shashi.todo.service.SearchService;

@Singleton
@Service
public class SearchServiceImpl implements SearchService {

	final static Logger logger = Logger.getLogger(SearchServiceImpl.class);

	@Autowired
	private JestClient jestClient;

	@Override
	public void create(Todo todo) {
		try {
			if (indexExists()) {
				Index index = new Index.Builder(todo).index("todo_app")
						.type("todo").build();
				jestClient.execute(index);
			}
		} catch (Exception e) {
			logger.error("Failed to add todo to the index", e);
		}
	}

	private boolean indexExists() {
		try {
			IndicesExists indicesExists = new IndicesExists.Builder("todo_app")
					.build();
			JestResult result = jestClient.execute(indicesExists);
			if (!result.isSucceeded()) {
				CreateIndex createIndex = new CreateIndex.Builder("todo_app")
						.build();
				jestClient.execute(createIndex);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to create index", e);
		}
		return false;
	}

	@Override
	public List<Todo> search(String query) {
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.multiMatchQuery(query,
					"title^5", "body"));

			Search search = new Search.Builder(searchSourceBuilder.toString())
					.addIndex("todo_app").addType("todo").build();
			JestResult result = jestClient.execute(search);
			return result.getSourceAsObjectList(Todo.class);
		} catch (IOException e) {
			logger.error("Search error", e);
		} catch (Exception e) {
			logger.error("Search error", e);
		}
		return null;
	}

	@Override
	public void update(Todo todo) {
		try {
			delete(todo.getTodoId());
			create(todo);
		} catch (Exception e) {
			logger.error("Failed to update todo in indiex	");
		}
	}

	@Override
	public void delete(String id) {
		try {
			jestClient.execute(new Delete.Builder(id).index("todo_app")
					.type("todo").build());
		} catch (Exception e) {
			logger.error("Failed to delete todo from the index", e);
		}
	}

	@Override
	public void deleteAll() {
		try {
			DeleteIndex deleteIndex = new DeleteIndex.Builder("todo_app").type(
					"todo").build();
			jestClient.execute(deleteIndex);
		} catch (Exception e) {
			logger.error("Failed to delete todo from the index", e);
		}
	}

}
