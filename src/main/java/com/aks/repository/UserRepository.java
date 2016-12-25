package com.aks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aks.domain.user.Users;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findById(Long id);

	Users findByEmail(String email);

	Users findByUserName(String userName);

	Users findByMobile(String mobile);

	List<Users> findAll();
}