package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security;

import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service.AuthenticationService;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service.TokenService;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service.UserDetailsServiceIMPL;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	UserDetailsServiceIMPL userDetailsService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	PasswordConfig passwordConfig;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		//Authentication auth = null;
		UserDetails user = null;
		
		Authentication successfulAuthenticatedToken = null;
		
		System.out.println("AUTHENTICATED PRINCIPAL: "+authentication.getPrincipal());
		
		if(authentication instanceof UsernamePasswordAuthenticationToken) {
			
			String username = authentication.getName();    			//getting username from the authentication object
			String password = authentication.getCredentials().toString(); 		//getting password from the authentication object
			
			user = userDetailsService.loadUserByUsername(username);
			
			if(passwordConfig.passwordEncoder().matches(user.getPassword(), password)){
				
					successfulAuthenticatedToken = 
						new UsernamePasswordAuthenticationToken(
								user,
								password,
								user.getAuthorities()
						);
			} 
			
			else {
				throw new BadCredentialsException("Invalid password");
			}
			
		}
	
		else if(authentication instanceof PreAuthenticatedAuthenticationToken) {
			
			try {
				user = tokenService.findByToken(authentication.getPrincipal().toString());
														           //token value
				
				successfulAuthenticatedToken = 
						new UsernamePasswordAuthenticationToken(
								user, 
								authentication.getCredentials().toString(),
								user.getAuthorities()
						);
				
			} 
			catch(Exception e) {
				throw new RuntimeException("Invalid Cookie");
			}
		}
		
		if(user == null) {
			return null;
		}
		
		return successfulAuthenticatedToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	/*
	 @Override
	 public boolean supports(Class<?> authentication) {
	    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	 }*/
	
	
	 /*
	   UserAuthenticationProvider class is not extending any specific class or implementing any interface that provides the 
	   setUserDetailsService and setPasswordEncoder methods. To use a custom UserAuthenticationProvider with Spring Security, 
	   you typically need to implement the AuthenticationProvider interface.
	*/
}
