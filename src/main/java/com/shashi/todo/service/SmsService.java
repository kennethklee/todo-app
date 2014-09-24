package com.shashi.todo.service;

import com.twilio.sdk.TwilioRestException;

public interface SmsService {
	void sendMessage(String to, String message) throws TwilioRestException;

}
