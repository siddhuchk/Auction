package com.aks.domain.user;

import java.util.HashMap;
import java.util.Map;

import com.aks.domain.Status;
import com.aks.web.controller.global.BadPracticeException;

/**
 * 
 * @author anuj.siddhu
 *
 */
public enum UserOTPStatus implements Status {
	ACTIVE(1, "Active"), INACTIVE(2, "Inactive") {
		@Override
		public boolean isDeleted() {
			return true;
		}
	};

	private final int id;
	private final String description;

	private UserOTPStatus(int id, String description) {
		this.id = id;
		this.description = description;
	}

	private static final Map<Integer, UserOTPStatus> statuses = new HashMap<Integer, UserOTPStatus>();

	static {
		for (UserOTPStatus status : UserOTPStatus.values()) {
			if (statuses.get(status.id) == null) {
				statuses.put(status.id, status);
			} else {
				throw new BadPracticeException("Duplicate id: " + status.id);
			}
		}
	}

	public static UserOTPStatus valueOf(int id) {
		return statuses.get(id);
	}

	public int id() {
		return id;
	}

	public String description() {
		return description;
	}
}
