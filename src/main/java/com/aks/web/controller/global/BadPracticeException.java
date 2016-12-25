package com.aks.web.controller.global;

public class BadPracticeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadPracticeException(String message) {
		super(message);
	}
}
