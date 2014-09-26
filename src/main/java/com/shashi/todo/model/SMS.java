package com.shashi.todo.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SMS {
	String destination;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
