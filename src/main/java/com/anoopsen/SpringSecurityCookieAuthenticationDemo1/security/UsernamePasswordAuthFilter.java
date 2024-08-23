package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter{

	//private static final ObjectMapper MAPPER = new ObjectMapper();
	//used to convert from Java Object(POJO) to JSON or, JSOn to Java Object(POJO)
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain
			)throws ServletException, IOException {
		
		final String loginURL = "/api/v1/auth/login";
		
		if(loginURL.equals(request.getServletPath()) && 
				HttpMethod.POST.matches(request.getMethod())) {
			
			//LoginDto loginDto = new LoginDto("anoop", "12345");
			
			
			try {
				
				 String jsonInput = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

			     // Convert the JSON to a LoginDto object
				 ObjectMapper objectMapper = new ObjectMapper();
			     
				 LoginDto loginDto = objectMapper.readValue(jsonInput, LoginDto.class);
				 
				 UsernamePasswordAuthenticationToken successfulAuthToken = 
							new UsernamePasswordAuthenticationToken(
									loginDto.getUsername(),
									loginDto.getPassword()
							);
					
					SecurityContextHolder.getContext().setAuthentication(successfulAuthToken);
					
					System.out.println("UsernamePasswordAuthenticationToken from UsernamePasswordAuthFilter: "+successfulAuthToken.getPrincipal().toString());
			     
				/*
				ServletInputStream inputStream = request.getInputStream();
				
				loginDto = MAPPER.readValue(inputStream, LoginDto.class);
				
				inputStream.close();
				*/
				/*
				ServletInputStream inputStream = request.getInputStream();
				
			    String requestBody = new BufferedReader(new InputStreamReader(inputStream))
			        .lines()
			        .collect(Collectors.joining("\n"));
			    
			    // Log or debug print the request body
			    System.out.println("Request Body received: " + requestBody);
			    
			    loginDto = MAPPER.readValue(requestBody, LoginDto.class);
				//used to convert the login credentials(as a stream of bytes) in JSON from request to the LoginDto object
				*/
				
				
			}
			catch(HttpMessageNotReadableException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		
		}
		filterChain.doFilter(request, response);
		
	}
	
}
