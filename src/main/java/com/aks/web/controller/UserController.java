package com.aks.web.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aks.domain.user.UserType;
import com.aks.domain.user.Users;
import com.aks.service.UserService;
import com.aks.utilities.ApplicationResponseCodes;
import com.aks.utilities.StringUtility;
import com.aks.web.controller.contants.ControllerURL;
import com.aks.web.dto.request.CreateUserRequest;
import com.aks.web.dto.response.CaptchaResponse;
import com.aks.web.dto.response.CreateUserResponse;
import com.aks.web.dto.response.ResponseDTO;
import com.aks.web.dto.response.ResponseHeaderDto;
import com.aks.web.dto.response.UsersResponseDto;
import com.aks.web.service.SessionService;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Controller
@RequestMapping(value = ControllerURL.DEFAULT_USER_URL)
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private Environment environment;
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private ReCaptchaImpl reCaptcha;

	@Resource(name = "blackListDomain")
	private Map<String, String> blackListDomains;
	@Resource(name = "activationUrlMap")
	private Map<String, Object> activationUrlMap;

	@PostConstruct
	public void init() {
		logger.info("Start User controller");
	}

	@RequestMapping(value = ControllerURL.CREATE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody Users users,
			@RequestHeader(required = false) String recaptcha_challenge_field,
			@RequestHeader(required = false) String recaptcha_response_field, ServletRequest servletRequest,
			BindingResult result, HttpSession session) throws Exception {

		UsersResponseDto<Users> response = new UsersResponseDto<Users>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();
		String remoteAddress = servletRequest.getRemoteAddr();
		logger.info("Create User Request raised from IP :" + remoteAddress + " user=" + users);

		if (!StringUtility.isBlank(users.getMobile()) || !StringUtility.isBlank(users.getCountryCode())) {
			if (!(users.getMobile().matches("^[0-9]{8,}")) || (!(users.getCountryCode().matches("^(\\+)(\\d{1,3})"))
					&& !(users.getCountryCode().matches("^[0-9]{1,3}")))) {
				responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_MOBILE_NUMBER.getErrorCode());
				responseHeader.setResponseMessage("Mobile Number/Country Code Format is not Valid");
				response.setHeaders(responseHeader);
				response.setBody(null);
				logger.info("Mobile Number or Country Code Field is not Valid. Mobile Numer:" + users.getMobile()
						+ " Country Code: " + users.getCountryCode() + " Member=" + users);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		if (isEmailInBlacklistedDomains(users.getEmail())) {
			responseHeader.setResponseCode(ApplicationResponseCodes.BLACK_LISTED_EMAIL.getErrorCode());
			responseHeader.setResponseMessage("Email in blackListed domains.");

			response.setHeaders(responseHeader);
			response.setBody(null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (result.hasErrors()) {
			logger.info("Validation Error in create  Member" + result);
			responseHeader.setResponseCode(ApplicationResponseCodes.VALIDATION_FAILED.getErrorCode());
			responseHeader.setResponseMessage("validation error");
			response.setHeaders(responseHeader);
			response.setBody(null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		String captcha = environment.getProperty("system.captcha");

		CaptchaResponse reCaptchaResponse;
		if (captcha.equalsIgnoreCase("realperson")) {
			reCaptchaResponse = checkRealPersionCaptcha(recaptcha_challenge_field, recaptcha_response_field);
		} else {
			ReCaptchaResponse captchaResponse = this.reCaptcha.checkAnswer(remoteAddress, recaptcha_challenge_field,
					recaptcha_response_field);

			if (null != captchaResponse && captchaResponse.isValid()) {
				reCaptchaResponse = new CaptchaResponse(true, "No Error");
			} else {
				reCaptchaResponse = new CaptchaResponse(false, "Validation failed");
			}
		}

		if (!reCaptchaResponse.isValid()) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_CAPTCHA.getErrorCode());
			responseHeader.setResponseMessage("Invalid Captcha");
			response.setHeaders(responseHeader);
			response.setBody(null);
		} else {
			try {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DAY_OF_MONTH,
						Integer.parseInt(activationUrlMap.get("mail.activation.expiry.day").toString()));
				Date expiryDate = c.getTime();

				String tokenValue = UUID.randomUUID().toString();
				users.setToken(tokenValue);
				users.setTokenExpiry(expiryDate);
				users.setUserType(UserType.CUSTOMER);

				users = userService.addUser(users);
				if (users != null) {
					users = removeSecuredFields(users);
					responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
					responseHeader.setResponseMessage("SUCCESS");
					response.setHeaders(responseHeader);
					response.setBody(users);
				}

			} catch (SQLIntegrityConstraintViolationException e) {
				logger.error("Create Member Error : " + e.getMessage());
				responseHeader.setResponseCode(
						ApplicationResponseCodes.MEMBER_REGISTRATION_FAILURE_MEMBER_EXISTS.getErrorCode());
				responseHeader.setResponseMessage("Member Already Exists");

				response.setHeaders(responseHeader);
				response.setBody(null);

			} catch (DataIntegrityViolationException e) {
				logger.error("Create Member Error : " + e.getMessage());
				responseHeader.setResponseCode(
						ApplicationResponseCodes.MEMBER_REGISTRATION_FAILURE_MEMBER_EXISTS.getErrorCode());
				responseHeader.setResponseMessage("Member Already Exists");

				response.setHeaders(responseHeader);
				response.setBody(null);
			} catch (Exception e) {
				logger.error("Create Member Error : " + e.getMessage());
				responseHeader.setResponseCode(ApplicationResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode());
				responseHeader.setResponseMessage("Internal Server Error");
				response.setHeaders(responseHeader);
				response.setBody(null);
			}
		}
		logger.info("Create Member reponse is : " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private CaptchaResponse checkRealPersionCaptcha(String challangeField, String responseField) {
		CaptchaResponse response = null;
		if (rpHash(responseField).equals(challangeField)) {
			response = new CaptchaResponse(true, null);
		} else {
			response = new CaptchaResponse(false, "Invalid Response");
		}
		return response;
	}

	private Object rpHash(String value) {
		int hash = 5381;
		value = value.toUpperCase();
		for (int i = 0; i < value.length(); i++) {
			hash = ((hash << 5) + hash) + value.charAt(i);
		}
		return String.valueOf(hash);
	}

	private boolean isEmailInBlacklistedDomains(String email) {
		String[] emails = email.split("@");
		if (emails != null && emails.length > 0) {
			try {
				return (StringUtils.isNotBlank(email) && blackListDomains.containsKey(emails[1])) ? true : false;
			} catch (ArrayIndexOutOfBoundsException ex) {
				return false;
			}
		} else
			return false;
	}

	private Users removeSecuredFields(Users user) {
		user.setPassword(null);
		return user;
	}

	@RequestMapping(value = ControllerURL.LOGIN, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody CreateUserRequest request, HttpServletRequest servletRequest)
			throws Exception {
		logger.info("Request Object:\n" + request);

		ResponseDTO<CreateUserResponse> response = new ResponseDTO<CreateUserResponse>();
		ResponseHeaderDto header = new ResponseHeaderDto();
		CreateUserResponse userResponse = new CreateUserResponse();
		userResponse.setEmail(request.getEmail());
		response.setHeaders(header);
		response.setBody(userResponse);
		logger.info("Response: " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}