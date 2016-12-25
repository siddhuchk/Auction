package com.aks.utilities;

public enum ApplicationConstants {

	SYSTEM_EMAIL_SENDER("mail.enterprise.sender"), SYSTEM_EMAIL_RECIVER(
			"mail.enterprise.default.receiver"), SYSTEM_WELCOME_EMAIL(
			"mail.welcome.subject"), MAIL_RESET_SUBJECT("mail.reset.subject"), MAIL_TICKET_UPDATION_SUBJECT(
			"mail.ticket.update.subject"), WELCOME_EMAIL_TEMPLATE(
			"mail.welcome.template"), RESET_EMAIL_TEMPLATE(
			"mail.reset.template"), MAIL_ACTIVATION_URL("mail.activation.url"), MAIL_ACTIVATION_EXPIRY_DAYS(
			"mail.activation.expiry.day"), MAIL_RESET_PASSWORD_URL(
			"mail.reset.url"), EMAIL_BLACK_LISTED_DOMAINS("blacklist.domain"), EMAIL_SMTP_HOST(
			"smtp.host"), EMAIL_SMTP_PORT("smtp.port"), EMAIL_SMTP_USERNAME(
			"smtp.username"), EMAIL_SMTP_PASSWORD("smtp.password"), DATE_FORMAT(
			"date.format"), APPLICATICATION_EMULATE("application.emulate"), SYSTEM_APP_REPOSITORY(
			"system.app.repository.path"), UDP_SERVER_PORT(
			"local.udp.server.port"), MAX_FILE_UPLOAD_LIMIT(
			"max.file.upload.limit");

	private String stringConstants;
	private Integer intConstants;

	ApplicationConstants(String stringConstants) {
		this.stringConstants = stringConstants;
	}

	ApplicationConstants(int intConstants) {
		this.intConstants = intConstants;
	}

	public String getStringConstants() {
		return stringConstants;
	}

	public Integer getIntConstants() {
		return intConstants;
	}
}
