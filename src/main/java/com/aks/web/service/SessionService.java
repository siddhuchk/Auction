package com.aks.web.service;

import com.aks.domain.Session;
import com.aks.domain.user.Users;

public interface SessionService {
	public String removeSession(String token, String ipAddress);

	public Session getSession(String sessionId);

	public String setSession(String sessionId, Users users);
}
