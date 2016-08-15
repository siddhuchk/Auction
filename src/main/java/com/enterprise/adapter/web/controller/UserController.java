package com.enterprise.adapter.web.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enterprise.adapter.domain.TestTable;
import com.enterprise.adapter.domain.Users;
import com.enterprise.adapter.service.TestTableService;
import com.enterprise.adapter.service.UserTableService;
import com.enterprise.adapter.web.controller.contants.ControllerURL;
import com.enterprise.adapter.web.dto.request.CreateUserRequest;
import com.enterprise.adapter.web.dto.request.Request;
import com.enterprise.adapter.web.dto.response.CreateUserResponse;
import com.enterprise.adapter.web.dto.response.Response;
import com.enterprise.adapter.web.dto.response.ResponseDTO;
import com.enterprise.adapter.web.dto.response.ResponseHeaderDto;
import com.enterprise.adapter.webservices.service.SessionService;
import com.enterprise.adapter.webservices.utilities.ApplicationResponseCodes;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Controller
@RequestMapping(value = ControllerURL.DEFAULT_USER_URL)
public class UserController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private Environment environment;
	@Autowired
	private UserTableService userTableService;
	@Autowired
	private SessionService sessionService;

	@PostConstruct
	public void init() {
		logger.info("Start SD Challenge");
	}


	@RequestMapping(value = ControllerURL.CREATE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request, HttpServletRequest servletRequest)
			throws Exception {
		logger.info("Request Object:\n" + request);

		ResponseDTO<CreateUserResponse> response = new ResponseDTO<CreateUserResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		CreateUserResponse userResponse = new CreateUserResponse();
		/* TODO */
		response.setHeaders(header);
		response.setBody(userResponse);

		Users users = userTableService.findByEmail(request.getEmail());
		if (users != null) {
			header.setResponseCode(ApplicationResponseCodes.USER_ALREADY_EXIST.getErrorCode());
			header.setResponseMessage("User email already exist");
		} else {
			users = new Users();
			users.setEmail(request.getEmail());
			users.setName(request.getName());
			users.setPhone(request.getPhone());
			userTableService.addNewRow(users);
			header.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
			header.setResponseMessage("success");

		}
		userResponse.setEmail(request.getEmail());
		userResponse.setName(request.getName());

		response.setHeaders(header);
		response.setBody(userResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.LOGIN, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody CreateUserRequest request, HttpServletRequest servletRequest)
			throws Exception {
		logger.info("Request Object:\n" + request);

		ResponseDTO<CreateUserResponse> response = new ResponseDTO<CreateUserResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		CreateUserResponse userResponse = new CreateUserResponse();

		Users users = userTableService.findByEmailAndPhone(request.getEmail(), request.getPhone());
		if (users == null) {
			header.setResponseCode(ApplicationResponseCodes.INVALID_CREDENTIALS.getErrorCode());
			header.setResponseMessage("Invalid email or phone");
		} else {
			String tokenId = sessionService.setSession(servletRequest.getSession().getId(), users);
			userResponse.setTokenId(tokenId);
			header.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
			header.setResponseMessage("Successfully login");
		}
		userResponse.setEmail(request.getEmail());
		response.setHeaders(header);
		response.setBody(userResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}