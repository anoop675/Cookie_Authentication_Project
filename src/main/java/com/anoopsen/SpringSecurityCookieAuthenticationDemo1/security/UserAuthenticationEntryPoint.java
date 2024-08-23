package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint{

	 private static final ObjectMapper MAPPER = new ObjectMapper();  
	 //used to convert Java Object(POJO) to JSON or, JSON to Java Object(POJO)
	 
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		 final String ERROR_MESSAGE = "You are unauthorized to access this URL path";
		
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	     response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	     MAPPER.writeValue(response.getOutputStream(), ERROR_MESSAGE);
		
	}

}
