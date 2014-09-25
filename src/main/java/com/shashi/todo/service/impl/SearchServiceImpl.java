package com.shashi.todo.service.impl;

import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;

import java.util.List;

import javax.inject.Singleton;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shashi.todo.model.Todo;
import com.shashi.todo.service.SearchService;

@Singleton
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private JestClient jestClient;

	@Override
	public void createIndex(Todo todo) {
		try {
			jestClient.execute(new CreateIndex.Builder("articles").build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Todo> getIndex(Todo todo) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));

		Search search = new Search.Builder(searchSourceBuilder.toString())
		// multiple index or types can be added.
				.addIndex("todo").build();

		SearchResult result;
		try {
			result = jestClient.execute(search);
			return result.getSourceAsObjectList(Todo.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			jestClient.execute(new Update.Builder(script).index("twitter")
					.type("tweet").id("1").build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteIndex(Todo todo) {
		try {
			jestClient.execute(new Delete.Builder("1").index("twitter")
					.type("tweet").build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
