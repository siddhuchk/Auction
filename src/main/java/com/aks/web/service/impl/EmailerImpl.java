package com.aks.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.Notification;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.aks.utilities.ApplicationConstants;
import com.aks.web.service.Emailer;

@Component
public class EmailerImpl implements Emailer {

	private static final Logger logger = LoggerFactory.getLogger(EmailerImpl.class);

	private final VelocityEngine velocityEngine;
	private final JavaMailSender mailSender;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	@Resource(name = "jpaEmailPropertyMap")
	Map<String, Object> jpaEmailPropertyMap;
	@Autowired
	Environment environment;

	@Autowired
	public EmailerImpl(VelocityEngine velocityEngine, JavaMailSender mailSender) {
		this.velocityEngine = velocityEngine;
		this.mailSender = mailSender;
	}

	@Override
	public void sendMail(Map<Object, Object> msgMap, final Map<String, Object> templateVariables,
			final String velocityTemplateName, String bbcEmailId) {
		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo((String) msgMap.get("to"));
		msg.setFrom((String) msgMap.get("from"));
		msg.setSubject((String) msgMap.get("subject"));
		if (bbcEmailId != null)
			msg.setBcc(bbcEmailId);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(msg.getTo());
				message.setBcc(msg.getBcc());
				message.setFrom(msg.getFrom(), "Fonetwish Enterprise");
				message.setSubject(msg.getSubject());

				logger.info("preparator : ");
				logger.info("from : " + msg.getFrom());
				logger.info("to : " + msg.getTo());
				logger.info("subject : " + msg.getSubject());
				String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplateName, "UTF-8",
						templateVariables);

				logger.info("body={}", body);

				message.setText(body, true);
				logger.info("subject : " + message.toString());
			}
		};
		logger.info("preparator : " + preparator.toString());
		taskExecutor.execute(new AsyncMailTask(preparator));

	}

	private class AsyncMailTask implements Runnable {

		private MimeMessagePreparator message;

		private AsyncMailTask(MimeMessagePreparator message) {
			this.message = message;
		}

		@Async
		public void run() {
			mailSender.send(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.u2opia.fonetwish.webservices.utilities.email.Emailer#
	 * sendOperatorsListMail(java.util.Map, java.util.Map, java.util.Map,
	 * java.lang.String)
	 */
	@Override
	public void sendOperatorsListMail(final Map<Object, Object> msgMap,
			final ArrayList<HashMap<String, String>> operatorsUSSDInfo, final Map<String, Object> templateVariables,
			final String velocityTemplateName, String bbCEmailId) {

		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo((String) msgMap.get("to"));
		msg.setFrom((String) msgMap.get("from"));
		msg.setSubject((String) msgMap.get("subject"));
		msg.setBcc(bbCEmailId);
		VelocityContext context = new VelocityContext();
		context.put("operatorsList", operatorsUSSDInfo);
		templateVariables.put("operatorsList", operatorsUSSDInfo);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(msg.getTo());
				message.setFrom(msg.getFrom(), "Fonetwish Enterprise");
				message.setSubject(msg.getSubject());

				logger.info("preparator : ");
				logger.info("from : " + msg.getFrom());
				logger.info("to : " + msg.getTo());
				logger.info("subject : " + msg.getSubject());
				msgMap.put("operatorsList", operatorsUSSDInfo);
				String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplateName, "UTF-8",
						templateVariables);

				logger.info("body={}", body);

				message.setText(body, true);
				logger.info("subject : " + message.toString());
			}
		};
		logger.info("preparator : " + preparator.toString());
		taskExecutor.execute(new AsyncMailTask(preparator));
		// mailSender.send(preparator);

		// logger.info("Sent e-mail to '{}'.", msg.getTo());

	}

	@Override
	public void sendHeartBeatEmail(final Map<Object, Object> msgMap, List<String> errorCode,
			final Map<String, Object> templateVariables, final String velocityTemplateName, String bbCEmailId) {

		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo((String) msgMap.get("to"));
		msg.setFrom((String) msgMap.get("from"));
		msg.setSubject((String) msgMap.get("subject"));
		VelocityContext context = new VelocityContext();
		context.put("errorCode", errorCode);
		templateVariables.put("errorCode", errorCode);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(msg.getTo());
				message.setFrom(msg.getFrom(), "Fonetwish Enterprise");
				message.setSubject(msg.getSubject());

				logger.info("preparator : ");
				logger.info("from : " + msg.getFrom());
				logger.info("to : " + msg.getTo());
				logger.info("subject : " + msg.getSubject());
				String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplateName, "UTF-8",
						templateVariables);

				logger.info("body={}", body);

				message.setText(body, true);
				logger.info("subject : " + message.toString());
			}
		};
		logger.info("preparator : " + preparator.toString());
		taskExecutor.execute(new AsyncMailTask(preparator));

	}

	public void sendAppApprovalRequestMail(final Map<Object, Object> msgMap,
			final ArrayList<HashMap<String, String>> operatorsUSSDInfo, final Map<String, Object> templateVariables,
			final String velocityTemplateName) {

		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo((String[]) msgMap.get("to"));
		msg.setFrom((String) msgMap.get("from"));
		msg.setSubject((String) msgMap.get("subject"));
		VelocityContext context = new VelocityContext();
		context.put("operatorsList", operatorsUSSDInfo);
		templateVariables.put("operatorsList", operatorsUSSDInfo);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(msg.getTo());
				message.setFrom(msg.getFrom(), "Fonetwish Enterprise");
				message.setSubject(msg.getSubject());

				logger.info("preparator : ");
				logger.info("from : " + msg.getFrom());
				logger.info("to : " + msg.getTo());
				logger.info("subject : " + msg.getSubject());
				msgMap.put("operatorsList", operatorsUSSDInfo);
				String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplateName, "UTF-8",
						templateVariables);

				logger.info("body={}", body);

				message.setText(body, true);
				logger.info("subject : " + message.toString());
			}
		};
		logger.info("preparator : " + preparator.toString());
		taskExecutor.execute(new AsyncMailTask(preparator));
		// mailSender.send(preparator);

		// logger.info("Sent e-mail to '{}'.", msg.getTo());

	}

	@Override
	public void sendRejectionEmail(Notification notification, String message, String bbCEmailId) {
		HashMap<String, Object> props = new HashMap<String, Object>();

		props.put("firstName", notification.getMember().getFirstName());
		props.put("message", message);
		props.put("appName", notification.getApplication().getName());
		HashMap<Object, Object> msg = new HashMap<Object, Object>();
		msg.put("from",
				(String) jpaEmailPropertyMap.get(ApplicationConstants.SYSTEM_EMAIL_SENDER.getStringConstants()));
		msg.put("to", notification.getMember().getEmail());
		msg.put("subject", jpaEmailPropertyMap.get("mail.application.rejected.subject"));
		try {
			sendMail(msg, props, (String) jpaEmailPropertyMap.get("mail.application.rejected.template"), bbCEmailId);
		} catch (Exception e) {
		}

	}
}