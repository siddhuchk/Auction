package com.enterprise.adapter.webservices.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.enterprise.adapter.domain.Session;
import com.enterprise.adapter.domain.Users;
import com.enterprise.adapter.webservices.service.SessionService;

/**
 * 
 * @author anuj.kumar2
 *
 */
@EnableScheduling
@Service
public class SessionServiceImpl implements SessionService {

	private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	private static ConcurrentHashMap<String, String> userMap = new ConcurrentHashMap<String, String>();

	@Autowired
	Environment environment;

	private static Integer expiryTime = 60;
	private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

	@PostConstruct
	public void init() {
		expiryTime = Integer.parseInt(environment.getProperty("session.time.out"));

	}

	@Scheduled(fixedDelay = 1000 * 60 * 10)
	public void destroySession() {

		Date d = new Date();
		for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
			Session session = entry.getValue();
			if (session.getExpiryTime().before(d)) {
				sessionMap.remove(entry.getKey());
				Users users = entry.getValue().getUsers();
				if (users != null) {
					userMap.remove(users.getId().toString());
				}
			}
		}
	}

	@Override
	public Session getSession(String sessionId) {

		Session session = sessionMap.get(sessionId);
		if (session != null) {

			Date d = new Date();
			if (session.getExpiryTime().before(d)) {
				sessionMap.remove(sessionId);
				return null;
			}
			session.setExpiryTime(getExpiryDate());
		}
		return session;
	}

	private Date getExpiryDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, expiryTime); // to get previous year add -1
		Date nextYear = cal.getTime();

		return nextYear;
	}

	@Override
	public String removeSession(String token, String ipAddress) {

		if (token != null) {
			Session session = sessionMap.get(token);

			if (session != null) {
				logger.info("User session logout : member is " + session.getUsers());
				if (session.getUsers() != null) {
					userMap.remove(session.getUsers().getId().toString());
				}
			} else {
				logger.info("INVALID REQUEST FOR LOGOUT : TOKEN =" + token + " user IP Address is :- " + ipAddress);
			}

		}
		sessionMap.remove(token);

		return token;
	}

	@Override
	public String setSession(String sessionId, Users user) {

		String token = "";
		if (userMap.containsKey(user.getId().toString())) {
			if (sessionMap.get(userMap.get(user.getId().toString())) != null)
				return userMap.get(user.getId().toString());
		}
		token = UUID.randomUUID().toString();
		Session session = new Session();
		session.setCreatedTime(new Date());
		session.setExpiryTime(getExpiryDate());
		session.setUsers(user);
		sessionMap.put(token, session);
		userMap.put(user.getId().toString(), token);

		return token;

	}
}
