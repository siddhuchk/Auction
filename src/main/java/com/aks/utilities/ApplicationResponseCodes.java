package com.aks.utilities;

public enum ApplicationResponseCodes {

	SUCCESS("200"), INVALID_CREDENTIALS("203"), BLACK_LISTED_EMAIL("206"), INVALID_CAPTCHA(
			"208"), URL_EXPIRED("201"), NOT_ALLOWED_BEFORE_EXIPRY("209"), VALIDATION_FAILED(
			"231"), ALREADY_ACTIVATED("270"), INVALID_USER("602"), MEMBER_REGISTRATION_FAILURE_MEMBER_NOT_ACTIVE(
			"207"), INVALID_MOBILE_NUMBER("611"),

	MEMBER_REGISTRATION_FAILURE_MEMBER_EXISTS("250"), SESSION_FAILURE_SESSION_EXPIRED(
			"251"), SESSION_FAILURE_SESSION_NOT_AVAILABLE("252"), MEMBER_EMAIL_NOT_FOUND(
			"340"), EMAIL_SEND_FAILUYRE("341"), NOT_BUSSINESS_EMAIL("342"),

	// Unhandeled Exceptions in application
	INTERNAL_SERVER_ERROR("500"),

	// Authority Issue starts from 400
	AUTHORIZATION_ERROR("401"), INVALID_CONFIGURATION("403"), NOT_AUTORIZED(
			"612"),
	// Database Issues starts from 500
	DATABASE_ERROR("501"),

	// Application name already used by the user
	APP_ALREADY_EXISTS("601"),
	// Application id provided by the user via API is invalid or APP not exist
	// in DB
	INVALID_APP("603"),
	// user tries to break the app live cycle flow
	INVALID_APP_WORKFLOW("604"),

	// Invalid uploaded test xls sheet by user
	INVALID_TEST_SHEET("605"),
	// NO test result found for app
	NO_TEST_RESULT("606"),
	// app not tested
	APP_NOT_TESTED("607"),
	// App test fail
	APP_TEST_FAIL("901"),
	// STILL TESTING
	APP_TEST_PROGRESS("902"),
	
	// MMCode Not Exist is DB
	MMCODE_NOT_EXISTS("609"),
	// MMCode is Premium, these code can be get by another API
	MMCODE_PREMIUM("610"),
	// MMCode used by another app

	INVALID_OPERATOR_COUNTRY_SELECTION("620"),

	// Calender Check, used in Analytics & Application End Date submited by the
	// user
	ENDDATE_CANT_LESS_CURRENTDATE("610"),

	// Application Emulator down or not responding for given request
	EMULATOR_DOWM("701"),
	
	// Story Error Codes
	STORY_ADDITION_FAILED("801"), STORY_DELETION_FAILED("802"), STORY_PRIORITY_FAILED(
			"803"), COMMENT_ADDITION_FAILED("804"), COMMENT_EDIT_FAILED("805"), COMMENT_DELETION_FAILED(
			"806"),
			
			USER_SUSPENDED("420"),
			
			APP_ID_NOT_FOUND("901");

	private String errorCode;

	ApplicationResponseCodes(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
