package com.shashi.todo.service.impl;

import java.util.List;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.apache.http.NameValuePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.SMS;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;

@RunWith(JMockit.class)
public class SmsServiceImplTest {

	@Tested
	private SmsServiceImpl smsServiceImpl;

	@Injectable
	private SmsServiceImpl smsService;

	@Injectable
	@Mocked
	private TwilioRestClient twilioclient;

	@Mocked
	private MessageFactory messageFactory;

	@Mocked
	private Account account;

	@Before
	public void setup() {

	}

	@Test
	public void testSendMessage() throws TwilioRestException {
		smsServiceImpl.setEnabled("Y");
		smsServiceImpl.setFrom("number");
		new Expectations() {
			{
				twilioclient.getAccount();
				result = account;
				account.getMessageFactory();
				result = messageFactory;
				messageFactory.create((List<NameValuePair>) any);
			}
		};
		smsServiceImpl.sendMessage("message");
	}

	@Test
	public void testSendMessageNotEnabled() throws TwilioRestException {
		smsServiceImpl.setEnabled("N");
		smsServiceImpl.setFrom("number");
		new Verifications() {
			{
				messageFactory.create((List<NameValuePair>) any);
				times = 0;
			}
		};
		smsServiceImpl.sendMessage("message");
	}

	@Test
	public void testRegister() throws BusinessException, TwilioRestException {
		SMS sms = new SMS();
		sms.setDestination("destination");
		new Expectations() {
			{
				twilioclient.getAccount();
				result = account;
				account.getMessageFactory();
				result = messageFactory;
				messageFactory.create((List<NameValuePair>) any);
			}
		};
		smsServiceImpl.register(sms);
		Assert.assertEquals("destination",
				System.getProperty(SmsServiceImpl.TWILIO_TO));
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
