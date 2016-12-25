/**
 * 
 */
package com.aks.web.dto.response;

import java.util.List;

import com.aks.domain.user.Users;

/**
 * @author karmveer.sharma
 *
 */
public class GetAllUsersResponse {
	private List<Users> users;

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "GetAllUsersResponse [users=" + users + "]";
	}

}
