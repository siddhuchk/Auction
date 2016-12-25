package com.aks.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.aks.domain.AbstractEntity;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Entity
@Table(name = "users_otp")
public class UsersOTP extends AbstractEntity<Long> {
	private static final long serialVersionUID = -3028114538116703622L;

	@Column(nullable = false)
	private long userId;
	@Column(unique = true, nullable = false)
	private String otpPasscode;
	private UserOTPStatus status;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOtpPasscode() {
		return otpPasscode;
	}

	public void setOtpPasscode(String otpPasscode) {
		this.otpPasscode = otpPasscode;
	}

	public UserOTPStatus getStatus() {
		return status;
	}

	public void setStatus(UserOTPStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsersOTP [userId=");
		builder.append(userId);
		builder.append(", otpPasscode=");
		builder.append(otpPasscode);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
