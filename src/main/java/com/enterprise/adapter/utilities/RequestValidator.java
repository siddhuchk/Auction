package com.enterprise.adapter.utilities;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.adapter.domain.Session;
import com.enterprise.adapter.domain.Users;
import com.enterprise.adapter.service.UserTableService;
import com.enterprise.adapter.webservices.service.SessionService;
import com.enterprise.adapter.webservices.utilities.ApplicationResponseCodes;

@Service
public class RequestValidator {

	@Autowired
	SessionService sessionService;

	@Autowired
	UserTableService userTableService;

	public Users validateUsers(String email, HttpServletRequest servletRequest) {
		String token = servletRequest.getHeader("tokenId");
		if (token == null) {
			throw new com.enterprise.adapter.web.controller.global.DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		Session session = sessionService.getSession(token);
		if (session == null) {
			throw new com.enterprise.adapter.web.controller.global.DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		Users Users = session.getUsers();
		if (Users == null) {
			throw new com.enterprise.adapter.web.controller.global.DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		if (!Users.getEmail().equals(email)) {
			throw new com.enterprise.adapter.web.controller.global.DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		return Users;
	}

	public Users getUsers(HttpServletRequest servletRequest) {
		String token = servletRequest.getHeader("UsersSessionId");
		if (token == null) {
			throw new com.enterprise.adapter.web.controller.global.DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);

		}
		Session session = sessionService.getSession(token);
		if (session == null) {
			throw new com.enterprise.adapter.web.controller.global.DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);

		}
		Users Users = session.getUsers();
		if (Users == null) {
			throw new com.enterprise.adapter.web.controller.global.DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}

		return Users;
	}
}
