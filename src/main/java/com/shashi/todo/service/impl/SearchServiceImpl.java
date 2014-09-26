package com.shashi.todo.service.impl;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shashi.todo.model.Todo;
import com.shashi.todo.service.SearchService;

@Singleton
@Service
public class SearchServiceImpl implements SearchService {

	final static Logger logger = LoggerFactory
			.getLogger(SearchServiceImpl.class);

	@Autowired
	private JestClient jestClient;

	@Override
	public void createIndex(Todo todo) {
		try {
			if (indexExists()) {
				Index index = new Index.Builder(todo).index("todo_app")
						.type("todo").build();
				jestClient.execute(index);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Todo> search(String query) {
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.multiMatchQuery(query,
					"title^3", "body"));

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
	public void updateIndex(Todo todo) {
		try {
			String script = "{\n"
					+ "    \"script\" : \"ctx._source.tags += tag\",\n"
					+ "    \"params\" : {\n" + "        \"tag\" : \"blue\"\n"
					+ "    }\n" + "}";

			jestClient.execute(new Update.Builder(script).index("todo")
					.type("todo").id(todo.getTodoId()).build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteIndex(Todo todo) {
		try {
			jestClient.execute(new Delete.Builder(todo.getTodoId())
					.index("todo").type("todo").build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
