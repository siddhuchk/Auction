package com.aks.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.aks.domain.user.Users;
import com.aks.repository.UserRepository;
import com.aks.service.UserService;
import com.aks.service.UsersInfoService;
import com.aks.utilities.ApplicationConstants;
import com.aks.utilities.ApplicationResponseCodes;
import com.aks.utilities.PasswordEncoder;
import com.aks.web.controller.global.DataAccessException;
import com.aks.web.service.impl.EmailerImpl;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Service
public class UsersServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskExecutor executor;
	@Autowired
	private UsersInfoService userInfoService;
	@Resource(name = "jpaEmailPropertyMap")
	private Map<String, Object> jpaEmailPropertyMap;
	@Autowired
	private Environment environment;
	@Resource
	private EmailerImpl emailService;

	@Override
	public Users addUser(Users user) throws SQLIntegrityConstraintViolationException, NoSuchAlgorithmException {
		user.setUuid(UUID.randomUUID().toString());
		String password = PasswordEncoder.passowdEncrpt(user.getPassword());
		user.setPassword(password);
		final Users userInfo = userRepository.save(user);

		executor.execute(new Runnable() {
			@Override
			public void run() {
				userInfoService.updateUserInfo(userInfo, false);
			}
		});
		return userInfo;
	}

	@Override
	public Users updateUser(Users user) {
		logger.debug("updating user: " + user);
		Users u = userInfoService.getUserByEmail(user.getEmail());
		if (u != null) {
			final Users userInfo = userRepository.save(user);

			executor.execute(new Runnable() {
				@Override
				public void run() {
					userInfoService.updateUserInfo(userInfo, false);
				}
			});
			return userInfo;
		}
		return null;
	}

	@Override
	public void deleteUser(Users user) {
		logger.debug("deleting user: " + user);
		userRepository.delete(user);

		executor.execute(new Runnable() {
			@Override
			public void run() {
				userInfoService.updateCache();
			}
		});
	}

	@Override
	public List<Users> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Users findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Users findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Users findBUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public Users findByMobile(String mobile) {
		return userRepository.findByMobile(mobile);
	}

	@Override
	public Users findByUuid(String uuid) {
		return userRepository.findByUuid(uuid);
	}

	@Override
	public Users activateUser(Users user) throws DataAccessException {
		boolean sendWelcomeEmail = true;
		if (user != null) {
			user.setToken(null);
			user.setTokenExpiry(null);
			if (user.getActivatedTime() != null) {
				sendWelcomeEmail = false;
			}
			user.setActivatedTime(new Date());
			user = updateUser(user);
		} else {
			return null;
		}

		if (sendWelcomeEmail) {
			// sendWelcomeEmail(user.getFirstName(), user.getEmail());
			// createForumLogin(user.getEmail(), user.getPassword());
		}
		return user;
	}

	@Override
	public void sendWelcomeEmail(String firstName, String email) {

		executor.execute(new Runnable() {
			@Override
			public void run() {
				HashMap<String, Object> props = new HashMap<String, Object>();
				props.put("firstName", firstName);
				Map<Object, Object> msg = new HashMap<>();
				msg.put("from", (String) jpaEmailPropertyMap
						.get(ApplicationConstants.SYSTEM_EMAIL_SENDER.getStringConstants()));
				msg.put("to", email);
				String adminEmailId = environment.getProperty("system.admin.email.id");
				msg.put("subject", environment.getProperty("mail.welcome.confirmation.subject"));
				try {
					emailService.sendMail(msg, props,
							(String) environment.getProperty("mail.welcome.confirmation.template"), adminEmailId);
				} catch (Exception e) {
					logger.info(String.format("Failed to send welcome mail to %s. reason : %s", email, e.getMessage()));
					throw new DataAccessException(
							"Failed to send welcome mail to" + email + " : error is " + e.getMessage(),
							ApplicationResponseCodes.INTERNAL_SERVER_ERROR);
				}
			}
		});

	}

	@Override
	public Users login(String email, String password) {
		Users user;
		try {
			user = userInfoService.getUserByEmail(email);
			if (user != null && user.getPassword().equals(password)) {
				return (Users) user.clone();
			}
		} catch (Exception e) {
			logger.debug("user not found in cache {}", email);
		}
		user = userRepository.findByEmailAndPassword(email, password);
		if (user != null)
			userInfoService.updateUserInfo(user, false);
		return user;
	}

	@Override
	public void sendConfirmationEmail(Users user, String activationLink) {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put("firstName", user.getFirstName());
		props.put("lastName", user.getLastName());
		props.put("activationLink", activationLink);
		HashMap<Object, Object> msg = new HashMap<Object, Object>();
		msg.put("from",
				(String) jpaEmailPropertyMap.get(ApplicationConstants.SYSTEM_EMAIL_SENDER.getStringConstants()));
		msg.put("to", user.getEmail());
		String adminEmailId = environment.getProperty("system.admin.email.id");
		msg.put("subject", jpaEmailPropertyMap.get("mail.welcome.subject"));
		try {
			emailService.sendMail(msg, props, (String) jpaEmailPropertyMap.get("mail.welcome.template"), adminEmailId);
		} catch (Exception e) {

			logger.info(
					String.format("Failed to send welcome mail to %s. reason : %s", user.getEmail(), e.getMessage()));
			throw new DataAccessException(
					"Failed to send welcome mail to" + user.getEmail() + " : error is " + e.getMessage(),
					ApplicationResponseCodes.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void resendVerificationEmail(Users user, String activationLink) throws DataAccessException {
		Users userByUuid = userRepository.findByUuid(user.getUuid());
		if (userByUuid != null) {
			sendConfirmationEmail(userByUuid, activationLink);
		} else {
			throw new DataAccessException("Invalid Member", ApplicationResponseCodes.INVALID_USER);
		}
	}

	@Override
	public boolean verifyEmail(Users user) {
		Users userByEmail = userRepository.findByEmail(user.getEmail());
		return (userByEmail != null) ? true : false;
	}

	@Override
	public Users findTokenByUser(Users user) {
		Users userByUuid = userRepository.findByUuid(user.getUuid());
		if (userByUuid == null) {
			throw new DataAccessException("Invalid user", ApplicationResponseCodes.INVALID_USER);
		}
		return userByUuid;
	}

	@Override
	public Users findUserByToken(String token, String userUuid) {
		Users userByUuid = userRepository.findByUuid(userUuid);
		if (userByUuid == null) {
			throw new DataAccessException("Invalid user", ApplicationResponseCodes.INVALID_USER);
		}
		if (userByUuid.getToken() != null) {
			if (!userByUuid.getToken().equalsIgnoreCase(token)) {
				throw new DataAccessException("Invalid user", ApplicationResponseCodes.URL_EXPIRED);
			}
		}
		return userByUuid;
	}

	@Override
	public void resendForgotPasswordEmail(Users user, String activationLink) throws DataAccessException {
		Users userByEmail = userRepository.findByUuid(user.getUuid());
		if (userByEmail != null) {
			sendResetpasswordEmail(userByEmail, activationLink);
		} else {
			throw new DataAccessException("Invalid Member", ApplicationResponseCodes.INVALID_USER);
		}

	}

	private void sendResetpasswordEmail(Users member, String activationLink) {
		HashMap<String, Object> props = new HashMap<String, Object>();
		props.put("firstName", member.getFirstName());
		props.put("lastName", member.getLastName());
		props.put("activationLink", activationLink);
		HashMap<Object, Object> msg = new HashMap<Object, Object>();
		msg.put("from", environment.getProperty(ApplicationConstants.SYSTEM_EMAIL_SENDER.getStringConstants()));
		msg.put("to", member.getEmail());
		msg.put("subject", environment.getProperty("mail.reset.subject"));
		String supportEmailId = environment.getProperty("system.support.email.id");
		try {
			emailService.sendMail(msg, props, (String) environment.getProperty("mail.reset.template"), supportEmailId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(
					String.format("Failed to send welcome mail to %s. reason : %s", member.getEmail(), e.getMessage()));
			throw new DataAccessException(
					"Failed to send welcome mail to" + member.getEmail() + " : error is " + e.getMessage(),
					ApplicationResponseCodes.INTERNAL_SERVER_ERROR);
		}
	}
}