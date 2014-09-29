package com.shashi.todo.service.impl;

import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.SMS;

@RunWith(JMockit.class)
public class SmsServiceImplTest {

	@Tested
	private SmsServiceImpl smsServiceImpl;

	@Injectable
	private SmsServiceImpl smsService;

	@Before
	public void setup() {

	}

	@Test
	public void testGetRegisteredNumber() throws BusinessException {
		System.setProperty(SmsServiceImpl.TWILIO_TO, "destination");
		SMS sms = smsServiceImpl.getRegisteredNumber();
		Assert.assertNotNull(sms);
	}

	@Test(expected = BusinessException.class)
	public void testGetRegisteredNumberExcep() throws BusinessException {
		SMS sms = smsServiceImpl.getRegisteredNumber();
	}

}
