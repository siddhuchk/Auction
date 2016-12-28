package com.aks.service;

import com.aks.domain.user.Users;

/**
 * 
 * @author anuj.siddhu
 *
 */
public interface UsersInfoService {

	public void updateCache();
	
	public Users getUserByEmail(String email);

	public void updateUserInfo(Users user, Boolean isDBupdate);

}