package com.aks.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.aks.domain.user.Users;

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

	void udpateRow(Users user);

	void deleteRow(Users user);
}
