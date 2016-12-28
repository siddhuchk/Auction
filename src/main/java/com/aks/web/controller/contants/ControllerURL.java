package com.aks.web.controller.contants;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class ControllerURL {
	public static final String DEFAULT_URL = "/sdc/";
	public static final String test = "test";
	public static final String DEFAULT_USER_URL = "/user/";
	public static final String CREATE = "create";
	public static final String LOGIN = "login";

	public static final String DEFAULT_AUCTION_URL = "/auction/";
	public static final String CREATE_PRODUCT_URL = "createProduct";
	public static final String GET_ALL_PRODUCT_URL = "getAllProduct";
	public static final String CREATE_BID_URL = "createBid";
	public static final String GET_BID_BY_PRODUCT_URL = "getBidByProduct";

	public static final String GET_WINNER_BY_PRODUCT_BID_URL = "getBidWinner";
	public static final String GET_WINNER = "getBid";

	public static final String RESULT = "result";
	public static final String GET_RESULT = "get";
	
	
	
	public static final String DEFAULT_MEMBER_URL = "/v1/member";
	public static final String GET_GROUPS_OF_MEMBER_URL = "/{id}/groups";
	public static final String ACTIVATE_MEMBER_URL = "/activate";
	public static final String MEMBER_LOGIN_URL = "/login";
	public static final String RESEND_VERIFICATION_EMAIL = "/resend";
	public static final String SEND_VERIFICATION_EMAIL = "/send";
	public static final String VERIFY_EMAIL = "/verifyEmail";
	public static final String RESET_PASSWORD = "/resetpassword";
	public static final String UPDATE_MEMBER_PROFILE_INFO = "/updateProfileInfo";
	public static final String LOGOUT = "/logout";
	public static final String CHANGE_PASSWORD = "/changepassword";
	public static final String CHANGE_PASSWORD_CONFIRMATION = "/changepasswordconfirmation";

	public static final String VALIDATE_TOKEN = "/validate";
	public static final String MEMBER_BANNED="/banned";
	public static final String MEMBER_REMOVEBAN="/removeban";

}