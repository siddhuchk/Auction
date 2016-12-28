package com.aks.web.controller.global;

import com.aks.utilities.ApplicationResponseCodes;

/**
 * 
 * @author anuj.siddhu
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