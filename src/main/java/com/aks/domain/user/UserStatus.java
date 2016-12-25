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
public enum UserStatus implements Status {
	ACTIVE(1, "Active"), INACTIVE(2, "Inactive") {
		@Override
		public boolean isDeleted() {
			return true;
		}
	};

	private final int id;
	private final String description;

	private UserStatus(int id, String description) {
		this.id = id;
		this.description = description;
	}

	private static final Map<Integer, UserStatus> statuses = new HashMap<Integer, UserStatus>();

	static {
		for (UserStatus status : UserStatus.values()) {
			if (statuses.get(status.id) == null) {
				statuses.put(status.id, status);
			} else {
				throw new BadPracticeException("Duplicate id: " + status.id);
			}
		}
	}

	public static UserStatus valueOf(int id) {
		return statuses.get(id);
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public String description() {
		return description;
	}
}
