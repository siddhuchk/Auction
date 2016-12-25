package com.aks.web.dto.response;

public class CaptchaResponse {

	private boolean valid;
	private String errorMessage;

	public CaptchaResponse(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
	}

	/**
	 * The reCaptcha error message. invalid-site-public-key
	 * invalid-site-private-key invalid-request-cookie incorrect-captcha-sol
	 * verify-params-incorrect verify-params-incorrect recaptcha-not-reachable
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * True if captcha is "passed".
	 * 
	 * @return
	 */
	public boolean isValid() {
		return valid;
	}

}
