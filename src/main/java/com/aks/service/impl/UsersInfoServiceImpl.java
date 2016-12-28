package com.aks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.aks.domain.user.Users;
import com.aks.repository.UserRepository;
import com.aks.service.UsersInfoService;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Service
public class UsersInfoServiceImpl implements UsersInfoService {

	@Autowired
	UserRepository userRepository;

	Map<String, Users> userInfo = new ConcurrentHashMap<String, Users>();

	@PostConstruct
	public void init() {
		updateCache();
	}

	@Override
	@Scheduled(cron = "0 0/30 * * * *")
	public void updateCache() {
		List<Users> userList = userRepository.findAll();

		if (userList != null && userList.size() > 0) {
			for (Users user : userList) {
				userInfo.put(user.getEmail(), user);
			}
		}
	}

	@Override
	public Users getUserByEmail(String email) {
		return userInfo.get(email);
	}

	@Override
	public void updateUserInfo(Users user, Boolean isDbUpdate) {
		Users user2;
		try {
			if (isDbUpdate == true) {
				userRepository.save(user);
			}
			user2 = (Users) user.clone();
		} catch (CloneNotSupportedException e) {
			user2 = user;
		}
		userInfo.put(user.getEmail(), user2);
	}
}