package com.shashi.todo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shashi.todo.model.BusinessException;
import com.shashi.todo.model.SMS;
import com.shashi.todo.service.SmsService;

@Component
@Path("/twilio")
public class TwilioCredential {

	@Autowired
	private SmsService smsService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response registerNumbers(SMS sms) throws BusinessException {
		smsService.register(sms);
		return Response.status(Response.Status.CREATED).entity(sms).build();
	}

	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRegisteredNumber() throws BusinessException {
		SMS sms = smsService.getRegisteredNumber();
		return Response.status(Response.Status.FOUND).entity(sms).build();
	}
}
