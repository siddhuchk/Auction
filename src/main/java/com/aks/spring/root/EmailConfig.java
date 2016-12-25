package com.aks.spring.root;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.aks.utilities.ApplicationConstants;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Configuration
@PropertySource(value = { "classpath:Configuration/mail.properties" })
@ImportResource({ "classpath:com/aks/spring/root/email-config.xml" })
public class EmailConfig {

	@Autowired
	private Environment environment;

	@Bean(name = "jpaEmailPropertyMap")
	public Map<String, Object> jpaEmailPropertyMap() {
		Map<String, Object> map = new HashMap<>();
		map.put(ApplicationConstants.SYSTEM_EMAIL_SENDER.getStringConstants(),
				environment
						.getProperty(ApplicationConstants.SYSTEM_EMAIL_SENDER
								.getStringConstants()));
		map.put("mail.enterprise.default.receiver",
				environment.getProperty("mail.enterprise.default.receiver"));
		map.put("mail.welcome.subject",
				environment.getProperty("mail.welcome.subject"));
		map.put("mail.welcome.template",
				environment.getProperty("mail.welcome.template"));
		map.put("mail.welcome.template",
				environment.getProperty("mail.welcome.template"));
		map.put("mail.reset.template",
				environment.getProperty("mail.reset.template"));
		map.put("mail.reset.subject",
				environment.getProperty("mail.reset.subject"));

		map.put("mail.ticket.update.template",
				environment.getProperty("mail.ticket.update.template"));
		map.put("mail.ticket.update.subject",
				environment.getProperty("mail.ticket.update.subject"));

		map.put("mail.operators.ussd.template",
				environment.getProperty("mail.operators.ussd.template"));
		map.put("mail.operators.ussd.subject",
				environment.getProperty("mail.operators.ussd.subject"));

		map.put("mail.contactus.template",
				environment.getProperty("mail.contactus.template"));
		map.put("mail.contactus.subject",
				environment.getProperty("mail.contactus.subject"));
		map.put("mail.contactus.constomercare.email",
				environment.getProperty("mail.contactus.constomercare.email"));

		map.put("system.admin.email.id",
				environment.getProperty("system.admin.email.id"));

		map.put("mail.system.alert.subject",
				environment.getProperty("mail.system.alert.subject"));
		map.put("mail.heartbeat.template",
				environment.getProperty("mail.heartbeat.template"));
		map.put("mail.systemAlert.template",
				environment.getProperty("mail.systemAlert.template"));

		map.put("app.approval.request.subject",
				environment.getProperty("app.approval.request.subject"));

		map.put("app.approval.request.template",
				environment.getProperty("app.approval.request.template"));

		map.put("mail.operators.failure.ussd.template",
				environment.getProperty("mail.operators.failure.ussd.template"));

		map.put("app.member.approval.request.subject",
				environment.getProperty("app.member.approval.request.subject"));

		map.put("app.member.approval.request.template",
				environment.getProperty("app.member.approval.request.template"));

		map.put("mail.operators.failure.ussd.subject",
				environment.getProperty("mail.operators.failure.ussd.subject"));

		map.put("mail.operators.failure.trailApp.template", environment
				.getProperty("mail.operators.failure.trailApp.template"));

		map.put("mail.operators.failure.trailApp.subject", environment
				.getProperty("mail.operators.failure.trailApp.subject"));

		map.put("mail.application.rejected.template",
				environment.getProperty("mail.application.rejected.template"));

		map.put("mail.application.rejected.subject",
				environment.getProperty("mail.application.rejected.subject"));

		return map;
	}

	@Bean(name = "activationUrlMap")
	public Map<String, Object> activationUrlMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mail.activation.url",
				environment.getProperty("mail.activation.url"));
		map.put("mail.activation.expiry.day",
				environment.getProperty("mail.activation.expiry.day"));
		map.put("mail.reset.url", environment.getProperty("mail.reset.url"));

		return map;
	}
}