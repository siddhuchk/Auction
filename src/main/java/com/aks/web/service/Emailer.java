package com.aks.web.service;

import java.util.Map;

public interface Emailer {

	/**
	 * @param msgMap
	 * @param templateVariables
	 * @param velocityTemplateName
	 */
	void sendMail(Map<Object, Object> msgMap, Map<String, Object> templateVariables, String velocityTemplateName,
			String bbCEmailId);

}
