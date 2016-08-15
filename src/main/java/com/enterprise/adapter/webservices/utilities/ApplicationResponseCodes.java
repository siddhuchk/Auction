package com.enterprise.adapter.webservices.utilities;

public enum ApplicationResponseCodes {

	SUCCESS("200"),
	
	INVALID_CREDENTIALS("201"), 
	VALIDATION_FAILED("202"),  
	INVALID_MOBILE_NUMBER("203"),
	USER_ALREADY_EXIST("204"),
	INVALID_USER("205"),
	
	SESSION_FAILURE_SESSION_EXPIRED("251"),
	
	INTERNAL_SERVER_ERROR("500"),
	DATABASE_ERROR("501");

	private String errorCode;

	ApplicationResponseCodes(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
