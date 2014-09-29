package com.shashi.todo.model;

import io.searchbox.annotations.JestId;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Todo implements Serializable {

	@JestId
	private String todoId;

	private String title;
	private String body;
	private boolean done;
	private Time createdTS;
	private Time updatedTS;

	public Todo() {
		this.done = false;
	}

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Time getCreatedTS() {
		return createdTS;
	}

	public void setCreatedTS(Time createdTS) {
		this.createdTS = createdTS;
	}

	public Time getUpdatedTS() {
		return updatedTS;
	}

	public void setUpdatedTS(Time updatedTS) {
		this.updatedTS = updatedTS;
	}

}
