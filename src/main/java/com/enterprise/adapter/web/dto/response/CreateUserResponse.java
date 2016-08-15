package com.enterprise.adapter.web.dto.response;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class CreateUserResponse {
	private String name;
	private String email;
	private String tokenId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
	public String toString() {
		return "CreateUserResponse [name=" + name + ", email=" + email + ", tokenId=" + tokenId + "]";
	}

	
}
