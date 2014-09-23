package com.shashi.todo.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExceptionReport {

	private String type;
	private String message;
	private String code;
	private String moreInfo;

	public ExceptionReport(String type, String message, String code,
			String moreInfo) {
		this.type = type;
		this.message = message;
		this.code = code;
		this.moreInfo = moreInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

}
