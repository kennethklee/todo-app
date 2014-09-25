package com.shashi.todo.model;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

@XmlRootElement
public class ErrorInfo {

	int status;

	int code;

	String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorInfo(BusinessException ex) {
		try {
			BeanUtils.copyProperties(ex, this);
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}

	public ErrorInfo(NotFoundException ex) {
		this.status = Response.Status.NOT_FOUND.getStatusCode();
		this.message = ex.getMessage();
	}

	public ErrorInfo() {
	}
}