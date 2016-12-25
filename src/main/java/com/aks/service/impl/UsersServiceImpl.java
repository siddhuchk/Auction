package com.aks.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.aks.domain.user.Users;
import com.aks.repository.UserRepository;
import com.aks.service.UserService;
import com.aks.utilities.PasswordEncoder;

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

	@Override
	public Users addUser(Users user) throws SQLIntegrityConstraintViolationException, NoSuchAlgorithmException {
		user.setUuid(UUID.randomUUID().toString());
		String password = PasswordEncoder.passowdEncrpt(user.getPassword());
		user.setPassword(password);
		final Users memberInfo = userRepository.save(user);

		executor.execute(new Runnable() {
			@Override
			public void run() {
				//TODO
			//	memberInfoService.updateMemberInfo(memberInfo, false);
			}
		});
		return memberInfo;
	}

	@Override
	public void udpateRow(Users user) {
		logger.debug("updating user: " + user);
		userRepository.save(user);
	}

	@Override
	public void deleteRow(Users user) {
		logger.debug("deleting user: " + user);
		userRepository.delete(user);
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
		// TODO Auto-generated method stub
		return null;
	}
}