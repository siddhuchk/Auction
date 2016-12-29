package com.aks.web.config;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aks.domain.Session;
import com.aks.utilities.ApplicationResponseCodes;
import com.aks.web.dto.response.ResponseDTO;
import com.aks.web.dto.response.ResponseHeaderDto;
import com.aks.web.service.SessionService;
import com.google.gson.Gson;

/**
 * 
 * @author anuj.siddhu
 *
 */
public class WebServiceInterceptor implements HandlerInterceptor {

	@Autowired
	SessionService service;

	// public Set<String> byPassURL;
	public String logoutURl;

	private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestType = request.getPathInfo();
		ResponseDTO<String> responseObj = new ResponseDTO<String>();
		ResponseHeaderDto header = new ResponseHeaderDto();

		String remoteAddress = request.getHeader("X-FORWARDED-FOR");
		if (remoteAddress == null) {
			remoteAddress = request.getRemoteAddr();
		}

		LOGGER.info("REQUEST URL: " + requestType + " FROM IP :" + remoteAddress);

		if (requestType.contains("/user/create") ||requestType.contains("/user/login") ) {
			return true;
		} else {
			String token = request.getHeader("tokenId");
			if (requestType.equalsIgnoreCase(logoutURl)) {
				LOGGER.info("LOGOUT REQUEST FOR TOKEN :" + token);
				service.removeSession(token, remoteAddress);
				return true;
			} else {
				if (token != null) {
					Session sesion = service.getSession(token);
					if (sesion != null) {
						if (sesion.getUsers() != null)
							return true;
					}

				}
			}
		}
		LOGGER.info("INVALID REQUEST URL: " + requestType + " FROM IP :" + remoteAddress);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson jsonObject = new Gson();
		header.setResponseCode(ApplicationResponseCodes.INVALID_CREDENTIALS.getErrorCode());
		responseObj.setHeaders(header);
		out.write(jsonObject.toJson(responseObj));
		out.close();
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2,
			Exception excetion) {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	public String getLogoutURl() {
		return logoutURl;
	}

	public void setLogoutURl(String logoutURl) {
		this.logoutURl = logoutURl;
	}
}
