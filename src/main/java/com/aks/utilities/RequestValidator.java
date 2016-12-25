package com.aks.utilities;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.domain.Session;
import com.aks.domain.user.Users;
import com.aks.service.UserService;
import com.aks.web.controller.global.DataAccessException;
import com.aks.webservices.service.SessionService;
import com.aks.webservices.utilities.ApplicationResponseCodes;

@Service
public class RequestValidator {

	@Autowired
	SessionService sessionService;

	@Autowired
	UserService userTableService;

	public Users validateUsers(String email, HttpServletRequest servletRequest) {
		String token = servletRequest.getHeader("tokenId");
		if (token == null) {
			throw new DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		Session session = sessionService.getSession(token);
		if (session == null) {
			throw new DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		Users Users = session.getUsers();
		if (Users == null) {
			throw new DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		if (!Users.getEmail().equals(email)) {
			throw new DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}
		return Users;
	}

	public Users getUsers(HttpServletRequest servletRequest) {
		String token = servletRequest.getHeader("UsersSessionId");
		if (token == null) {
			throw new DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);

		}
		Session session = sessionService.getSession(token);
		if (session == null) {
			throw new DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);

		}
		Users Users = session.getUsers();
		if (Users == null) {
			throw new DataAccessException(
					"Invalid Access : session object not found", ApplicationResponseCodes.INVALID_USER);
		}

		return Users;
	}
}
