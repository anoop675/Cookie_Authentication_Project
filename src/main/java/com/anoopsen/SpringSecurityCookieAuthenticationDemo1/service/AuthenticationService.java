package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.LoginDto;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.RegisterDto;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.Role;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.User;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.repository.UserRepository;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security.PasswordConfig;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security.SecurityConfig;

@Service
public class AuthenticationService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordConfig passwordConfig;
	
	public UserDetails register(RegisterDto registerDto) {
		
		var user = User
					.builder()
					.email(registerDto.getEmail())
					.username(registerDto.getUsername())
					.password(passwordConfig.passwordEncoder().encode(registerDto.getPassword()))
					.token("token")
					.role(Role.USER)
					.build()
					;
							
		userRepo.save(user);
		
		return user;
	}
	
	/*
	public UserDetails login(LoginDto loginDto) {
		
		Authentication auth = 
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsername(),
						loginDto.getPassword()
				
		);
		
		authManager.authenticate(auth);
		
		var user = userRepo.findByUsername(loginDto.getUsername()).orElseThrow();
		
		return user;
	}*/
	
}
