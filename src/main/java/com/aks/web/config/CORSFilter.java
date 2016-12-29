package com.aks.web.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * @author anuj.siddhu
 *
 */
public class CORSFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"GET,POST,PUT,DELETE,OPTIONS");

		StringBuilder headersByPass = new StringBuilder("x-requested-with,")
				.append(request.getHeader("access-control-request-headers"));

		
		response.setHeader("Access-Control-Allow-Headers",
				headersByPass.toString());
		
		filterChain.doFilter(request, response);
	}
}
