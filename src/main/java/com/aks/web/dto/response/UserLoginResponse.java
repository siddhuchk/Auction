package com.aks.web.dto.response;

import com.aks.domain.user.Users;

/**
 * 
 * @author anuj.siddhu
 *
 */
public class UserLoginResponse {
	private String userSessionId;
	private Users user;

	public String getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLoginResponse [userSessionId=");
		builder.append(userSessionId);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}
}