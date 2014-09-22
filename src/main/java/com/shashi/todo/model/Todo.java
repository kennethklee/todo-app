package com.shashi.todo.model;

import java.util.UUID;

public class Todo {

	private String todoId;

	private String title;

	private String body;

	private boolean done;

	public Todo() {
		this.done = false;
		this.todoId = UUID.randomUUID().toString();
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

}
