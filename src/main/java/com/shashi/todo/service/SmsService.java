package com.shashi.todo.service;

import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.SMS;

public interface SmsService {
	public void sendMessage(String message);

	public void register(SMS sms);

	public SMS getRegisteredNumber() throws BusinessException;

}
