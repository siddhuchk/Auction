package com.aks.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.domain.user.Users;
import com.aks.repository.UserRepository;
import com.aks.service.UserService;

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

	@Override
	public Users addUser(Users user) {
		logger.debug("adding user: " + user);
		return userRepository.save(user);
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