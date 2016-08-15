package com.enterprise.adapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enterprise.adapter.domain.Users;

@Repository
public interface UserTableRepository extends JpaRepository<Users, Integer> {

	Users findById(Long id);

	Users findByEmail(String email);
	
	Users findByEmailAndPhone(String email, String phone);

	List<Users> findAll();
}
