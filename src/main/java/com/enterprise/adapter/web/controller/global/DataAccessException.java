package com.enterprise.adapter.web.controller.global;

import com.enterprise.adapter.webservices.utilities.ApplicationResponseCodes;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class DataAccessException extends org.springframework.dao.DataAccessException {

	private static final long serialVersionUID = -8013707243374913125L;
	private ApplicationResponseCodes errorCode;

	public DataAccessException(String msg) {
		super(msg);
		errorCode = ApplicationResponseCodes.DATABASE_ERROR;
	}

	public DataAccessException(String msg, ApplicationResponseCodes errorCode) {
		this(msg);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public ApplicationResponseCodes getErrorCode() {
		return errorCode;
	}
}
