package com.aks.web.dto.response;

import java.util.Date;

public class UsersRequestDto {

	private Long id;
	private String memberGuid;
	private String userName;
	private String email;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private Integer mobileCountryCode;
	private String mobileNumber;
	private String registerGuid;
	private Integer serviceClassId;
	private String imageUrl;
	private String status;
	private Date createdTime;
	private Date updatedTime;
	private Date activatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberGuid() {
		return memberGuid;
	}

	public void setMemberGuid(String memberGuid) {
		this.memberGuid = memberGuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getMobileCountryCode() {
		return mobileCountryCode;
	}

	public void setMobileCountryCode(Integer mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRegisterGuid() {
		return registerGuid;
	}

	public void setRegisterGuid(String registerGuid) {
		this.registerGuid = registerGuid;
	}

	public Integer getServiceClassId() {
		return serviceClassId;
	}

	public void setServiceClassId(Integer serviceClassId) {
		this.serviceClassId = serviceClassId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getActivatedTime() {
		return activatedTime;
	}

	public void setActivatedTime(Date activatedTime) {
		this.activatedTime = activatedTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberRequestDto [id=");
		builder.append(id);
		builder.append(", memberGuid=");
		builder.append(memberGuid);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", role=");
		builder.append(role);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", mobileCountryCode=");
		builder.append(mobileCountryCode);
		builder.append(", mobileNumber=");
		builder.append(mobileNumber);
		builder.append(", registerGuid=");
		builder.append(registerGuid);
		builder.append(", serviceClassId=");
		builder.append(serviceClassId);
		builder.append(", imageUrl=");
		builder.append(imageUrl);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createdTime=");
		builder.append(createdTime);
		builder.append(", updatedTime=");
		builder.append(updatedTime);
		builder.append(", activatedTime=");
		builder.append(activatedTime);
		builder.append("]");
		return builder.toString();
	}

}
