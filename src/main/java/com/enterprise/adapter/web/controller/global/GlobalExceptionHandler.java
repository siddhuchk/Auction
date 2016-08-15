package com.enterprise.adapter.web.controller.global;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 
 * @author anuj.kumar2
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> ExceptionHandler(HttpServletRequest request,
			Exception excetion) {

		excetion.printStackTrace();
		String requestType = request.getPathInfo();
		logger.error("Error occured in application for the url ="
				+ requestType);
		String remoteAddress = request.getHeader("X-FORWARDED-FOR");
		if (remoteAddress == null) {
			remoteAddress = request.getRemoteAddr();
		}
		return null;

	}

}
