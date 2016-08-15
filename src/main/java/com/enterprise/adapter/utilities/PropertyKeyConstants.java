package com.enterprise.adapter.utilities;

/**
 * 
 * @author anuj.kumar2
 *
 */
public enum PropertyKeyConstants {

	PROPERTY_NAME_JDBC_DRIVER_CLASSNAME("jdbc.driver.classname"), PROPERTY_NAME_JDBC_URL(
			"jdbc.url"), PROPERTY_NAME_JDBC_USERNAME("jdbc.username"), PROPERTY_NAME_JDBC_PASSWORD(
			"jdbc.password"), PROPERTY_NAME_U2OPIA_NOTIFY_WEBSERVICES(
			"u2opia-fonetwish-webservices"), PROPERTY_NAME_JDBC_SHOW_SQL(
			"jdbc.show.sql"), PROPERTY_NAME_JDBC_GENERATE_DDL(
			"jdbc.generate.ddl"), PROPERTY_NAME_JDBC_DATABASE("jdbc.database"), PROPERTY_NAME_JDBC_DATABASE_PLATFORM(
			"jdbc.database.platform"), PROPERTY_NAME_JDBC_DATABASE_PLATFORM1(
			"jdbc.database.platform"), PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE(
			"hibernate.jdbc.batch_size"), DATE_FORMAT("date.format"), MAX_CONNECTIONAGE_IN_SECONDS(
			"max.connection.age.in.seconds");

	private String keyConstants;

	PropertyKeyConstants(String keyConstants) {
		this.keyConstants = keyConstants;
	}

	public String getKeyConstants() {
		return keyConstants;
	}
}