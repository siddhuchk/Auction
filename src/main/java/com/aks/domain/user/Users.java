package com.aks.domain.user;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.aks.domain.AbstractEntity;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Entity
@Table(name = "users")
public class Users extends AbstractEntity<Long>implements Cloneable {
	private static final long serialVersionUID = -3028114538116703621L;

	@Column(unique = true, nullable = false)
	private String uuid;

	@Column(unique = true, nullable = false)
	@Size(max = 100, min = 3)
	@Pattern(regexp = "^[a-zA-Z0-9_]+$")
	private String userName;

	@Size(max = 100, min = 5)
	@Column(unique = true, nullable = false)
	@Email
	private String email;

	@NotEmpty
	private String password;

	@Size(max = 100, min = 1)
	private String firstName;
	private String lastName;

	private String countryCode;
	@Column(unique = true)
	private String mobile;

	private UserType userType = UserType.CUSTOMER;
	private UserStatus status = UserStatus.ACTIVE;

	private String token = null;
	private Date tokenExpiry;
	private Date activatedTime;

	private Boolean isBanned;

	@Column(length = 512)
	private String reasonForSuspension = null;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpiry() {
		return tokenExpiry;
	}

	public void setTokenExpiry(Date tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	public Date getActivatedTime() {
		return activatedTime;
	}

	public void setActivatedTime(Date activatedTime) {
		this.activatedTime = activatedTime;
	}

	public Boolean getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public String getReasonForSuspension() {
		return reasonForSuspension;
	}

	public void setReasonForSuspension(String reasonForSuspension) {
		this.reasonForSuspension = reasonForSuspension;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Users [uuid=");
		builder.append(uuid);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", countryCode=");
		builder.append(countryCode);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", status=");
		builder.append(status);
		builder.append(", token=");
		builder.append(token);
		builder.append(", tokenExpiry=");
		builder.append(tokenExpiry);
		builder.append(", isBanned=");
		builder.append(isBanned);
		builder.append(", reasonForSuspension=");
		builder.append(reasonForSuspension);
		builder.append("]");
		return builder.toString();
	}
}