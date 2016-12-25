package com.aks.web.dto.response;

public class InteractiveSessionDTO {

	private String sessionId;
	private String txId;
	private String userId;
	private String requestType;
	private Object data;
	private String operatorName;
	private String countryName;
	private String requestId;
	private String notification;

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the txId
	 */
	public String getTxId() {
		return txId;
	}

	/**
	 * @param txId
	 *            the txId to set
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName
	 *            the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @param requestId
	 *            the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the notification
	 */
	public String getNotification() {
		return notification;
	}

	/**
	 * @param notification
	 *            the notification to set
	 */
	public void setNotification(String notification) {
		this.notification = notification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InteractiveSessionDTO [");
		if (sessionId != null) {
			builder.append("sessionId=");
			builder.append(sessionId);
			builder.append(", ");
		}
		if (txId != null) {
			builder.append("txId=");
			builder.append(txId);
			builder.append(", ");
		}
		if (userId != null) {
			builder.append("userId=");
			builder.append(userId);
			builder.append(", ");
		}
		if (requestType != null) {
			builder.append("requestType=");
			builder.append(requestType);
			builder.append(", ");
		}
		if (data != null) {
			builder.append("data=");
			builder.append(data);
			builder.append(", ");
		}
		if (operatorName != null) {
			builder.append("operatorName=");
			builder.append(operatorName);
			builder.append(", ");
		}
		if (countryName != null) {
			builder.append("countryName=");
			builder.append(countryName);
			builder.append(", ");
		}
		if (requestId != null) {
			builder.append("requestId=");
			builder.append(requestId);
			builder.append(", ");
		}
		if (notification != null) {
			builder.append("notification=");
			builder.append(notification);
		}
		builder.append("]");
		return builder.toString();
	}

}
