/**
 * 
 */
package com.enterprise.adapter.web.dto.response;

import java.util.List;

import com.enterprise.adapter.domain.Users;

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
