package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieAuthenticationFilter extends OncePerRequestFilter{

	public static final String COOKIE_NAME = "auth_by_cookie";
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain
			)throws ServletException, IOException {
		
		Optional<Cookie> cookieAuth = 
					Stream.of(
						Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])
					)
					.filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
					.findFirst();
		/*
		 	it looks for a specific cookie named "auth_by_cookie" in the incoming request's cookies. 
		 	It does this by iterating over the request's cookies and searching for a cookie with the
		 	 name "auth_by_cookie."
			If it finds the cookie, it wraps it in an Optional and stores it in cookieAuth.
			NOTE: request.getCookies() returns an array of Cookies in the request. 
		 */
		
		if(cookieAuth.isPresent()) {
			
			System.out.println("Cookie found in request: "+cookieAuth.get().getValue());
			
			PreAuthenticatedAuthenticationToken successfulAuthToken = 
					new PreAuthenticatedAuthenticationToken(
						 cookieAuth.get().getValue(),          // is used as the principal, which usually represents the cookie value.
						 null								   // no credentials(password) is required, as authentication is solely based on the "auth_by_cookie" cookie 
					);
			
			/*
			 	PreAuthenticatedAuthenticationToken is used to store encrypted tokens without the
			 	need to decrypt them, it can be saved in the SecurityContextHolder as it is.
			 	
			 	UsernamePasswordAuthenticationToken is used to store, user's username (principal),
			 	password (credentials), and granted authorities in the SecurityContextHolder.
			 	(If the token is decrypted, and the user's username, password and authorities are known,
			 	then we can use UsernamePasswordAuthenticationToken)
			 */
			
			System.out.println("PreAuthenticatedAuthenticationToken from Cookie Filter: "
			+successfulAuthToken.getPrincipal().toString());
			
			SecurityContextHolder.getContext().setAuthentication(successfulAuthToken);
			
		}
		else if(!cookieAuth.isPresent())
			System.out.println("Cookie NOT found in request to "+request.getServletPath());
		
		filterChain.doFilter(request, response);							
		
	}
}
