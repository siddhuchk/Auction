package com.aks.domain;

/**
 * 
 * @author anuj.siddhu
 *
 */
public interface Status {
	int id();

	String name();

	String description();

	default boolean isDeleted() {
		return false;
	}
}
