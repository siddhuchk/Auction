package com.aks.web.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.aks.domain.user.UserStatus;
import com.aks.domain.user.UserType;
import com.aks.domain.user.Users;
import com.aks.service.UserService;
import com.aks.utilities.ApplicationResponseCodes;
import com.aks.utilities.PasswordEncoder;
import com.aks.utilities.RequestValidator;
import com.aks.utilities.StringUtility;
import com.aks.web.controller.contants.ControllerURL;
import com.aks.web.controller.global.DataAccessException;
import com.aks.web.dto.response.CaptchaResponse;
import com.aks.web.dto.response.ResponseHeaderDto;
import com.aks.web.dto.response.UserLoginResponse;
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
	ReCaptchaImpl reCaptcha;
	@Resource(name = "blackListDomain")
	private Map<String, String> blackListDomains;
	@Resource(name = "activationUrlMap")
	private Map<String, Object> activationUrlMap;
	@Autowired
	private RequestValidator requestValidator;

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
						+ " Country Code: " + users.getCountryCode() + " Users=" + users);
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
			logger.info("Validation Error in create  Users" + result);
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

					String activationLink = activationUrlMap.get("mail.activation.url").toString();
					URL url = emailUrl(users, activationLink, true);

					if (url != null)
						userService.resendVerificationEmail(users, url.toString());
				}

			} catch (SQLIntegrityConstraintViolationException e) {
				logger.error("Create Users Error : " + e.getMessage());
				responseHeader.setResponseCode(
						ApplicationResponseCodes.MEMBER_REGISTRATION_FAILURE_MEMBER_EXISTS.getErrorCode());
				responseHeader.setResponseMessage("Users Already Exists");

				response.setHeaders(responseHeader);
				response.setBody(null);

			} catch (DataIntegrityViolationException e) {
				logger.error("Create Users Error : " + e.getMessage());
				responseHeader.setResponseCode(
						ApplicationResponseCodes.MEMBER_REGISTRATION_FAILURE_MEMBER_EXISTS.getErrorCode());
				responseHeader.setResponseMessage("Users Already Exists");

				response.setHeaders(responseHeader);
				response.setBody(null);
			} catch (Exception e) {
				logger.error("Create Users Error : " + e.getMessage());
				responseHeader.setResponseCode(ApplicationResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode());
				responseHeader.setResponseMessage("Internal Server Error");
				response.setHeaders(responseHeader);
				response.setBody(null);
			}
		}
		logger.info("Create Users reponse is : " + response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.VERIFY_EMAIL, method = RequestMethod.POST)
	public ResponseEntity<?> verifyEmail(@RequestBody Users user, HttpSession session) {

		UsersResponseDto<Users> response = new UsersResponseDto<Users>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		try {
			if (userService.verifyEmail(user)) {
				user = removeSecuredFields(user);
				responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
				responseHeader.setResponseMessage("SUCCESS");
				response.setHeaders(responseHeader);
				response.setBody(user);
			} else {
				responseHeader.setResponseCode(ApplicationResponseCodes.MEMBER_EMAIL_NOT_FOUND.getErrorCode());
				responseHeader.setResponseMessage("Email Doesn't exist");
				response.setHeaders(responseHeader);
				response.setBody(null);
			}

		} catch (Exception ex) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode());
			responseHeader.setResponseMessage("Email verification Failed");
			response.setHeaders(responseHeader);
			response.setBody(null);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.SEND_VERIFICATION_EMAIL, method = RequestMethod.POST)
	public ResponseEntity<?> sendVerificationEmail(@RequestBody Users user) {

		URL url;
		UsersResponseDto<Users> response = new UsersResponseDto<Users>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		if (user == null || user.getUuid() == null) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_USER.getErrorCode());
		} else {
			user = userService.findByUuid(user.getUuid());
			String activationLink = activationUrlMap.get("mail.activation.url").toString();
			url = emailUrl(user, activationLink, true);

			if (url != null) {
				userService.resendVerificationEmail(user, url.toString());
				user = removeSecuredFields(user);
				responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
				responseHeader.setResponseMessage("SUCCESS");
				response.setHeaders(responseHeader);
				response.setBody(user);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.RESEND_VERIFICATION_EMAIL, method = RequestMethod.POST)
	public ResponseEntity<?> reSendVerificationEmail(@RequestBody Users user) {

		UsersResponseDto<Users> response = new UsersResponseDto<Users>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		if (user == null || user.getUuid() == null) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_USER.getErrorCode());
			response.setHeaders(responseHeader);
		} else {
			user = userService.findByUuid(user.getUuid());
			URL url;
			String activationLink = activationUrlMap.get("mail.activation.url").toString();
			url = emailUrl(user, activationLink, false);
			if (url != null) {
				userService.resendVerificationEmail(user, url.toString());
				user = removeSecuredFields(user);
				responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
				responseHeader.setResponseMessage("SUCCESS");
				response.setHeaders(responseHeader);
				response.setBody(user);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.ACTIVATE_MEMBER_URL, method = RequestMethod.POST)
	public ResponseEntity<?> activateUser(@RequestHeader("token") String token, @RequestHeader("uuid") String uuid) {
		if (logger.isDebugEnabled()) {
			logger.debug("ACTIVATE_USER_URL request , token = " + token);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();
		Users user = null;
		try {
			user = userService.findUserByToken(token, uuid);
			if (user.getToken() == null) {
				responseHeader.setResponseCode(ApplicationResponseCodes.ALREADY_ACTIVATED.getErrorCode());
				responseHeader.setResponseMessage("ALREADY ACTIVATED");
				response.setHeaders(responseHeader);
				response.setBody(null);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception ex) {
			responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
			responseHeader.setResponseMessage("SUCCESS");
			response.setHeaders(responseHeader);
			response.setBody(null);
		}
		Integer status = 0;

		if (user != null) {
			Calendar date = Calendar.getInstance();
			String expirtyTime = environment.getProperty("mail.activation.expiry.day");
			Integer expriryTime = Integer.parseInt(expirtyTime.trim());
			if (date.getTimeInMillis() - user.getTokenExpiry().getTime() <= expriryTime * 60 * 60 * 1000) {
				status = 0; // valid and inactive
			} else
				status = 1; // expired token
		} else {
			status = 2; // expired url
		}

		if (status == 0) {
			user = userService.activateUser(user);
			responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
			responseHeader.setResponseMessage("SUCCESS");
			response.setHeaders(responseHeader);
			response.setBody(null);
		} else if (status == 1) {
			responseHeader.setResponseCode("201");
			responseHeader.setResponseMessage("EMAIL CONFIRMATION CODE EXPIRED");
			response.setHeaders(responseHeader);
			response.setBody(null);
		} else if (status == 2) {
			responseHeader.setResponseCode(ApplicationResponseCodes.URL_EXPIRED.getErrorCode());
			responseHeader.setResponseMessage("USER NOT AVAILABLE WITH GIVEN CODE");
			response.setHeaders(responseHeader);
			response.setBody(null);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ACTIVATE_USER_URL repospnse is " + response);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.MEMBER_LOGIN_URL, method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestHeader("email") String userName,
			@RequestHeader(required = false) String password,
			@RequestHeader(required = false) String recaptcha_challenge_field,
			@RequestHeader(required = false) String recaptcha_response_field, HttpServletRequest servletRequest)
					throws NoSuchAlgorithmException {
		logger.info("Users login request received {}", userName + " : user password is :" + password);
		password = PasswordEncoder.passowdEncrpt(password);

		UsersResponseDto<UserLoginResponse> response = new UsersResponseDto<UserLoginResponse>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		CaptchaResponse reCaptchaResponse = null;
		String remoteAddress = servletRequest.getHeader("X-FORWARDED-FOR");
		if (remoteAddress == null) {
			remoteAddress = servletRequest.getRemoteAddr();
		}
		// boolean isBlokedIp = blackListedService.checkIp(remoteAddress);
		if (StringUtils.isNotEmpty(StringUtils.trim(recaptcha_response_field))
				&& StringUtils.isNotEmpty((StringUtils.trim(recaptcha_challenge_field)))) {
			String captcha = environment.getProperty("system.captcha");
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

			if (null != reCaptchaResponse && reCaptchaResponse.isValid()) {
				buildLoginResponse(userName, password, response, responseHeader, servletRequest.getSession().getId(),
						remoteAddress);
			} else {
				logger.info("Invalid Captcha entered by User emial=" + userName);
				responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_CAPTCHA.getErrorCode());
				responseHeader.setResponseMessage("Invalid Captcha");
				response.setHeaders(responseHeader);
				response.setBody(null);
			}
		} else {
			buildLoginResponse(userName, password, response, responseHeader, servletRequest.getSession().getId(),
					remoteAddress);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.LOGOUT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> logout(@RequestHeader Long userId, HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("LOGOUT request from user :" + userId);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		sessionService.removeSession(session.getId(), null);
		session.invalidate();
		responseHeader.setResponseCode("200");
		responseHeader.setResponseMessage("SUCCESS");
		response.setHeaders(responseHeader);
		response.setBody("SUCCESS");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.RESET_PASSWORD, method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@RequestHeader String emailId) {

		UsersResponseDto<Users> response = new UsersResponseDto<Users>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();
		Users user = userService.findByEmail(emailId);
		if (user == null) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_USER.getErrorCode());
			responseHeader.setResponseMessage("INVALID USER");
			response.setHeaders(responseHeader);
		} else {
			URL url;
			String resetLink = activationUrlMap.get("mail.reset.url").toString();
			url = emailUrl(user, resetLink, false);
			userService.resendForgotPasswordEmail(user, url.toString());
			user = removeSecuredFields(user);
			responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
			responseHeader.setResponseMessage("SUCCESS");
			response.setHeaders(responseHeader);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.CHANGE_PASSWORD, method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestHeader String uuid, @RequestHeader String oldPassword,
			@RequestHeader String newpassword) throws NoSuchAlgorithmException {

		if (logger.isDebugEnabled()) {
			logger.debug("CHANGE_PASSWORD request is : uuid= " + uuid + ",  oldPassword=" + oldPassword
					+ ", newpassword =" + newpassword);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		Users user = userService.findByUuid(uuid);
		oldPassword = PasswordEncoder.passowdEncrpt(oldPassword);
		newpassword = PasswordEncoder.passowdEncrpt(newpassword);
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newpassword);
			userService.updateUser(user);
			responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
			responseHeader.setResponseMessage("PASSWORD CHANGED");
			response.setHeaders(responseHeader);
			response.setBody("SUCCESS");
		} else {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_CREDENTIALS.getErrorCode());
			responseHeader.setResponseMessage("USER PASSWORD IS INVALID");
			response.setHeaders(responseHeader);
			response.setBody(null);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("CHANGE_PASSWORD reeponse is : " + response);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.CHANGE_PASSWORD_CONFIRMATION, method = RequestMethod.POST)
	public ResponseEntity<?> changePasswordConfirmation(@RequestHeader String token, @RequestHeader String newPassword,
			@RequestHeader("uuid") String uuid) throws NoSuchAlgorithmException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"CHANGE_PASSWORD_CONFIRMATION request recvied , token=:" + token + " newpassword =" + newPassword);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();
		newPassword = PasswordEncoder.passowdEncrpt(newPassword);
		Users user = null;
		try {
			user = userService.findUserByToken(token, uuid);
			if (user.getToken() == null) {
				responseHeader.setResponseCode(ApplicationResponseCodes.ALREADY_ACTIVATED.getErrorCode());
				responseHeader.setResponseMessage("ALREADY ACTIVATED");
				response.setHeaders(responseHeader);
				response.setBody(null);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception ex) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode());
			responseHeader.setResponseMessage("");
			response.setHeaders(responseHeader);
			response.setBody("INVALID USER");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		Integer status = 0;
		if (user != null) {
			Calendar date = Calendar.getInstance();
			String expirtyTime = environment.getProperty("mail.activation.expiry.day");
			// date.add(Calendar.DATE, );
			Integer expriryTime = Integer.parseInt(expirtyTime.trim());
			if (date.getTimeInMillis() - user.getTokenExpiry().getTime() <= expriryTime * 60 * 60 * 1000) {
				status = 0;
			} else
				status = 1;
		}
		if (status == 0) {
			user.setPassword(newPassword);
			user.setToken(null);
			user.setTokenExpiry(null);
			if (user.getActivatedTime() == null) {
				user.setActivatedTime(Calendar.getInstance().getTime());
				userService.sendWelcomeEmail(user.getFirstName(), user.getEmail());
			}
			userService.updateUser(user);
			responseHeader.setResponseCode("200");
			responseHeader.setResponseMessage("SUCCESS");
			response.setHeaders(responseHeader);
			response.setBody(null);
		} else if (status == 1) {
			responseHeader.setResponseCode("201");
			responseHeader.setResponseMessage("EMAIL CONFIRMATION CODE EXPIRED, RESENDING EMAIL");
			response.setHeaders(responseHeader);
			response.setBody(null);
		} else if (status == 2) {
			responseHeader.setResponseCode("-1");
			responseHeader.setResponseMessage("USER NOT AVAILABLE WITH GIVEN CODE");
			response.setHeaders(responseHeader);
			response.setBody(null);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("CHANGE_PASSWORD_CONFIRMATION reponse is " + response);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.VALIDATE_TOKEN, method = RequestMethod.POST)
	public ResponseEntity<?> validateTokenRequest(@RequestHeader String token, @RequestHeader("uuid") String uuid)
			throws NoSuchAlgorithmException {
		if (logger.isDebugEnabled()) {
			logger.debug("validate token request recvied , token=:" + token + " uuid =" + uuid);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		Users user = null;
		user = userService.findUserByToken(token, uuid);
		if (user != null) {
			Calendar date = Calendar.getInstance();
			String expirtyTime = environment.getProperty("mail.activation.expiry.day");
			Integer expriryTime = Integer.parseInt(expirtyTime.trim());
			if (date.getTimeInMillis() - user.getTokenExpiry().getTime() <= expriryTime * 60 * 60 * 1000) {

				responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
				responseHeader.setResponseMessage("");
				response.setHeaders(responseHeader);
				response.setBody(null);
			} else {
				responseHeader.setResponseCode(ApplicationResponseCodes.URL_EXPIRED.getErrorCode());
			}
			response.setHeaders(responseHeader);
			response.setBody(null);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" token status reponse is " + response);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.UPDATE_MEMBER_PROFILE_INFO, method = RequestMethod.POST)
	public ResponseEntity<?> updateUserProfile(@RequestParam(required = true, value = "uuid") String uuid,
			@RequestParam(required = false, value = "mobileNumber") String mobileNumber,
			HttpServletRequest servletRequest) {

		if (logger.isDebugEnabled()) {
			logger.debug(" UPDATE_USER_PROFILE_INFO request =" + uuid + " mobile NUmber =" + mobileNumber);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		Users user = null;
		try {
			user = requestValidator.validateUsers(uuid, servletRequest);
			if (user == null) {
				responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_USER.getErrorCode());
				responseHeader.setResponseMessage("INVALID USER");
				response.setHeaders(responseHeader);
				response.setBody("INVALID USER");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			user = userService.findByUuid(uuid);
			if (mobileNumber != null && mobileNumber.trim() != "")
				user.setMobile(mobileNumber);
			userService.updateUser(user);
		} catch (Exception ex) {
			logger.error("UPDATE_USER_PROFILE_INFO error " + ex.getMessage());
			responseHeader.setResponseCode(ApplicationResponseCodes.INTERNAL_SERVER_ERROR.getErrorCode());
			responseHeader.setResponseMessage("INVALID USER DATA");
			response.setHeaders(responseHeader);
			response.setBody("INVALID USER DATA");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		responseHeader.setResponseCode("200");
		responseHeader.setResponseMessage("SUCCESS");
		response.setHeaders(responseHeader);
		response.setBody("SUCCESS");
		if (logger.isDebugEnabled()) {
			logger.debug("UPDATE_USER_PROFILE_INFO reponse is " + response);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.MEMBER_BANNED, method = RequestMethod.POST)
	public ResponseEntity<?> suspendUser(@RequestHeader(required = true) String bannedUserUuid,
			@RequestHeader(required = true) String adminUuid, @RequestBody(required = true) String reasonForSuspension,
			HttpServletRequest servletRequest) throws NoSuchAlgorithmException {
		if (logger.isDebugEnabled()) {
			logger.debug("Users Suspend Request Received for uuid : " + bannedUserUuid);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		Users user = null;
		user = requestValidator.validateUsers(adminUuid, servletRequest);
		if (user == null || user.getUserType() != UserType.ADMINISTRATOR) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_USER.getErrorCode());
			response.setHeaders(responseHeader);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		Users userToBan = userService.findByUuid(bannedUserUuid);
		userToBan.setIsBanned(true);
		userToBan.setReasonForSuspension(reasonForSuspension);
		userService.updateUser(userToBan);
		if (logger.isDebugEnabled()) {
			logger.debug(" Banned User reponse is " + response);
		}
		responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
		response.setHeaders(responseHeader);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = ControllerURL.MEMBER_REMOVEBAN, method = RequestMethod.POST)
	public ResponseEntity<?> removesuspendMember(@RequestHeader String removeBanUserUuid,
			@RequestHeader("adminUuid") String adminUuid, HttpServletRequest servletRequest)
					throws NoSuchAlgorithmException {
		if (logger.isDebugEnabled()) {
			logger.debug("Users Unblock Request Recieved for user with removeBanUserUuid" + removeBanUserUuid);
		}
		UsersResponseDto<String> response = new UsersResponseDto<String>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		Users user = null;
		user = requestValidator.validateUsers(adminUuid, servletRequest);
		if (user == null || user.getUserType() != UserType.ADMINISTRATOR) {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_USER.getErrorCode());
			response.setHeaders(responseHeader);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		Users userToUnblock = userService.findByUuid(removeBanUserUuid);
		userToUnblock.setIsBanned(false);
		userService.updateUser(userToUnblock);
		if (logger.isDebugEnabled()) {
			logger.debug(" Banned Remove reponse is " + response);
		}
		responseHeader.setResponseCode(ApplicationResponseCodes.SUCCESS.getErrorCode());
		response.setHeaders(responseHeader);
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

	private URL emailUrl(Users user, String activationLink, Boolean isFirstRequest) {
		URL url = null;
		if (!isFirstRequest) {
			if (user.getEmailTimerRequired() == true) {
				validateEmailRequest(user);
			} else {
				user.setEmailTimerRequired(true);
			}
		}
		Calendar c = Calendar.getInstance();
		user.setToken(UUID.randomUUID().toString());
		c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(activationUrlMap.get("mail.activation.expiry.day").toString()));
		user.setTokenExpiry(c.getTime());
		activationLink = activationLink + user.getToken() + "&uuid=" + user.getUuid();
		try {
			url = new URL(activationLink);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		userService.updateUser(user);
		return url;
	}

	private void validateEmailRequest(Users user) {
		if (user.getToken() != null && user.getTokenExpiry() != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(user.getTokenExpiry());
			c.add(Calendar.DAY_OF_MONTH,
					-Integer.parseInt(activationUrlMap.get("mail.activation.expiry.day").toString()));
			Calendar cal2 = Calendar.getInstance();
			c.add(Calendar.MINUTE, 15);
			if (cal2.getTime().before(c.getTime())) {
				throw new DataAccessException(null, ApplicationResponseCodes.NOT_ALLOWED_BEFORE_EXIPRY);
			}
		}
	}

	private void buildLoginResponse(String userName, String password, UsersResponseDto<UserLoginResponse> response,
			ResponseHeaderDto responseHeader, String sessionId, String remoteAddress) {

		Users user = userService.login(userName, password);
		if (user != null) {
			logger.info("Login failed for user :, Account is not activated " + user);
			if (user.getActivatedTime() == null || user.getStatus() == UserStatus.INACTIVE) {
				throw new DataAccessException("Login Failed, Account is Not Activated: user is" + userName,
						ApplicationResponseCodes.MEMBER_REGISTRATION_FAILURE_MEMBER_NOT_ACTIVE);
			}
			user = removeSecuredFields(user);
			if (user.getIsBanned() == true) {
				responseHeader.setResponseCode(ApplicationResponseCodes.USER_SUSPENDED.getErrorCode());
				responseHeader.setResponseMessage("User is Suspended by Admin, Blocked");
				response.setHeaders(responseHeader);
				response.setBody(null);
			} else {
				responseHeader.setResponseCode("200");
				responseHeader.setResponseMessage("SUCCESS");

				response.setHeaders(responseHeader);

				UserLoginResponse userLogin = new UserLoginResponse();
				String tokenId = sessionService.setSession(sessionId, user);

				userLogin.setUser(user);
				userLogin.setUserSessionId(tokenId);
			}
		} else {
			responseHeader.setResponseCode(ApplicationResponseCodes.INVALID_CREDENTIALS.getErrorCode());
			responseHeader.setResponseMessage("Wrong UserName and/or Password");
			response.setHeaders(responseHeader);
			response.setBody(null);
		}
	}
}