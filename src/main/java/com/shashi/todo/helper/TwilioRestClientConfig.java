package com.shashi.todo.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.twilio.sdk.TwilioRestClient;

@Configuration
public class TwilioRestClientConfig {

	@Value("#{systemEnvironment['TWILIO_SID']}")
	private String accountSID;

	@Value("#{systemEnvironment['TWILIO_TOKEN']}")
	private String authToken;

	@Bean
	public TwilioRestClient twilioRestClient() {
		TwilioRestClient client = new TwilioRestClient(accountSID, authToken);
		return client;
	}

}
