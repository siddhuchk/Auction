package com.aks.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.aks.domain.user.Users;
import com.aks.web.controller.global.DataAccessException;

/**
 * 
 * @author anuj.siddhu
 *
 */
public interface UserService {
	Users addUser(Users user) throws SQLIntegrityConstraintViolationException, NoSuchAlgorithmException;

	Users findById(Long id);
	
	Users findByUuid(String uuid);

	Users findByEmail(String email);

	Users findBUserName(String userName);

	Users findByMobile(String mobile);

	List<Users> findAll();

	Users updateUser(Users user);

	void deleteUser(Users user);
	
	Users activateUser(Users user) throws DataAccessException;
	
	void sendWelcomeEmail(String firstName, String email);
	
	Users login(String email, String password);
	
	void sendConfirmationEmail(Users user, String activationLink);

	void resendVerificationEmail(Users user, String activationLink)
			throws DataAccessException;

	boolean verifyEmail(Users user);

	Users findTokenByUser(Users user);

	Users findUserByToken(String token, String userUuid);
	
	void resendForgotPasswordEmail(Users user, String activationLink)
			throws DataAccessException;
}