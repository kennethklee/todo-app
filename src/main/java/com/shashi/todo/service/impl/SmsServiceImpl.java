package com.shashi.todo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shashi.todo.service.SmsService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;

@Service
public class SmsServiceImpl implements SmsService {

	@Value("#{systemEnvironment['TWILIO_SID']}")
	private String accountSID;

	@Value("#{systemEnvironment['TWILIO_TOKEN']}")
	private String authToken;

	@Value("#{systemEnvironment['TWILIO_FROM']}")
	private String from;

	@Override
	public void sendMessage(String to, String message) {
		try {
			TwilioRestClient client = new TwilioRestClient(accountSID,
					authToken);
			Account mainAccount = client.getAccount();
			MessageFactory messageFactory = mainAccount.getMessageFactory();
			final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
			messageParams.add(new BasicNameValuePair("To", to));
			messageParams.add(new BasicNameValuePair("From", from));
			messageParams.add(new BasicNameValuePair("Body", message));

			messageFactory.create(messageParams);
		} catch (TwilioRestException supressed) {
			supressed.printStackTrace();
		}
	}

}
