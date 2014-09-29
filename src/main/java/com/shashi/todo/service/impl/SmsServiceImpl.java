package com.shashi.todo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.shashi.todo.helper.TodoConstants;
import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.SMS;
import com.shashi.todo.service.SmsService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;

@Service
public class SmsServiceImpl implements SmsService {

	final static Logger logger = Logger.getLogger(SmsServiceImpl.class);

	public static final String TWILIO_TO = "TWILIO_TO";

	public static final String TWILIO_FROM = "TWILIO_FROM";

	public static final String TWILIO_ENABLED = "TWILIO_ENABLED";

	public static final String TWILIO_SID = "TWILIO_SID";

	public static final String TWILIO_TOKEN = "TWILIO_TOKEN";

	@Override
	public void sendMessage(String message) {
		String enabled = System.getenv(TWILIO_ENABLED);
		String to = System.getProperty(TWILIO_TO);
		String from = System.getProperty(TWILIO_FROM);
		String sid = System.getProperty(TWILIO_SID);
		String token = System.getProperty(TWILIO_TOKEN);

		if (!(TodoConstants.ENABLED).equals(enabled))
			return;
		try {
			sendMessage(to, from, sid, token, message);
		} catch (TwilioRestException supressed) {
			logger.error("Failed to send sms", supressed);
		}
	}

	@Override
	public void register(SMS sms) throws BusinessException {
		if (sms != null) {
			String to = sms.getTo();
			String from = sms.getFrom();
			String sid = sms.getSid();
			String token = sms.getToken();
			String enabled = (sms.getEnabled()) ? TodoConstants.ENABLED
					: TodoConstants.DISABLED;
			if (StringUtils.isNotEmpty(to) && StringUtils.isNotEmpty(from)) {
				try {
					sendMessage(to, from, sid, token,
							TodoConstants.PHONE_REGISTRAION_MSG);
				} catch (TwilioRestException e) {
					System.setProperty(TWILIO_ENABLED, TodoConstants.DISABLED);
					throw new BusinessException(
							Response.Status.BAD_REQUEST.getStatusCode(),
							e.getErrorMessage(),
							TodoConstants.NUMBER_REGISTRATION_FAILED);
				}
				setSMSProperty(to, from, sid, token, enabled);
			}
		}

	}

	private void setSMSProperty(String to, String from, String sid,
			String token, String enabled) {
		System.setProperty(TWILIO_TO, to);
		System.setProperty(TWILIO_FROM, from);
		System.setProperty(TWILIO_SID, sid);
		System.setProperty(TWILIO_TOKEN, token);
		System.setProperty(TWILIO_ENABLED, enabled);
	}

	private void sendMessage(String to, String from, String sid, String token,
			String message) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(sid, token);
		Account mainAccount = client.getAccount();
		MessageFactory messageFactory = mainAccount.getMessageFactory();
		final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
		messageParams.add(new BasicNameValuePair("To", to));
		messageParams.add(new BasicNameValuePair("From", from));
		messageParams.add(new BasicNameValuePair("Body", message));
		messageFactory.create(messageParams);
	}

	@Override
	public SMS getRegisteredNumber() throws BusinessException {
		String number = System.getProperty(TWILIO_TO);
		if (StringUtils.isNotEmpty(number)) {
			SMS sms = new SMS();
			sms.setTo(System.getProperty(TWILIO_TO));
			sms.setFrom(System.getProperty(TWILIO_FROM));
			sms.setEnabled(TodoConstants.ENABLED.equals(System
					.getProperty(TWILIO_ENABLED)));
			return sms;
		}
		throw new BusinessException(Response.Status.NOT_FOUND.getStatusCode(),
				"No telephone number registered yet",
				TodoConstants.NO_DATA_FOUND);
	}
}
