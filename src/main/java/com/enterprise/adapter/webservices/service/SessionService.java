package com.enterprise.adapter.webservices.service;

import com.enterprise.adapter.domain.Session;
import com.enterprise.adapter.domain.Users;

public interface SessionService {
	public String removeSession(String token, String ipAddress);

	public Session getSession(String sessionId);

	public String setSession(String sessionId, Users users);
}
