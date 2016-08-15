package com.enterprise.adapter.web.dto.response;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class Response {
	private String name;
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Response [name=" + name + ", message=" + message + "]";
	}
	
}