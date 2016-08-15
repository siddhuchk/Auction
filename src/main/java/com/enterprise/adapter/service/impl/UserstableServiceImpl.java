package com.enterprise.adapter.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.adapter.domain.Users;
import com.enterprise.adapter.repository.UserTableRepository;
import com.enterprise.adapter.service.UserTableService;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Service
public class UserstableServiceImpl implements UserTableService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserstableServiceImpl.class);

	@Autowired
	private UserTableRepository userTableRepository;

	@Override
	public Users addNewRow(Users user) {
		return userTableRepository.save(user);
	}

	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return userTableRepository.findAll();
	}

	@Override
	public Users findByEmail(String email) {
		// TODO Auto-generated method stub
		return userTableRepository.findByEmail(email);
	}

	@Override
	public void udpateRow(Users user) {
		userTableRepository.save(user);

	}

	@Override
	public void deleteRow(Users user) {
		userTableRepository.delete(user);

	}

	@Override
	public Users findByEmailAndPhone(String email, String phone) {
		return userTableRepository.findByEmailAndPhone(email, phone);
	}
}