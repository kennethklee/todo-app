package com.shashi.todo.helper;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JestClientConfig {

	@Value("#{systemEnvironment['SEARCHBOX_URL']}")
	private String searchboxUrl;

	@Bean
	public JestClient jestClient() {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(searchboxUrl)
				.multiThreaded(true).build());
		return factory.getObject();
	}
}
