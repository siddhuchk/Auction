package com.aks.domain.user;

import java.util.HashMap;
import java.util.Map;

import com.aks.web.controller.global.BadPracticeException;

/**
 * 
 * @author anuj.siddhu
 *
 */
public enum UserType {
	CUSTOMER(1, "Customer"), ADMINISTRATOR(2, "Administrator");

	private static final Map<Integer, UserType> types = new HashMap<Integer, UserType>();

	static {
		for (UserType type : UserType.values()) {
			if (types.get(type.id) == null) {
				types.put(type.id, type);
			} else {
				throw new BadPracticeException("Duplicate id: " + type.id);
			}
		}
	}
	private final int id;
	private final String description;

	private UserType(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public static UserType valueOf(int id) {
		return types.get(id);
	}

	public int id() {
		return id;
	}

	public String description() {
		return description;
	}
}