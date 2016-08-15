package com.enterprise.adapter.service;

import java.util.List;

import com.enterprise.adapter.domain.Users;

public interface UserTableService {
	Users addNewRow(Users user);

	List<Users> findAll();

	Users findByEmail(String email);
	
	Users findByEmailAndPhone(String email, String phone);

	void udpateRow(Users user);

	void deleteRow(Users user);
}
