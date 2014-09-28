package com.shashi.todo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Autowired
	private TwilioRestClient twilioclient;

	public static final String TWILIO_TO = "TWILIO_TO";

	@Value("#{systemEnvironment['TWILIO_FROM']}")
	private String from;

	@Value("#{systemEnvironment['TWILIO_ENABLED']}")
	private String enabled;

	@Override
	public void sendMessage(String message) {
		if (!("Y").equals(enabled))
			return;
		try {
			Account mainAccount = twilioclient.getAccount();
			MessageFactory messageFactory = mainAccount.getMessageFactory();
			final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
			messageParams.add(new BasicNameValuePair("To", System
					.getProperty(TWILIO_TO)));
			messageParams.add(new BasicNameValuePair("From", from));
			messageParams.add(new BasicNameValuePair("Body", message));
			messageFactory.create(messageParams);
		} catch (TwilioRestException supressed) {
			logger.error("Failed to send sms", supressed);
		}
	}

	@Override
	public void register(SMS sms) {
		if (sms != null) {
			if (StringUtils.isNotEmpty(sms.getDestination())) {
				String number = parseAsTelNum(sms.getDestination());
				System.setProperty(TWILIO_TO, number);
			}
		}

	}

	private String parseAsTelNum(String source) {
		return source;
	}

	@Override
	public SMS getRegisteredNumber() throws BusinessException {

		String number = System.getProperty(TWILIO_TO);
		if (StringUtils.isNotEmpty(number)) {
			SMS sms = new SMS();
			sms.setDestination(number);
			return sms;
		}
		throw new BusinessException(Response.Status.NOT_FOUND.getStatusCode(),
				"No telephone number registered yet",
				TodoConstants.NO_DATA_FOUND);
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
}
