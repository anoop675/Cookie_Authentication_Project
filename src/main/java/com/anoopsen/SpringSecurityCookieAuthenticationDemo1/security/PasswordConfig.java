package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfig {
	
	public PasswordEncoder passwordEncoder() {
		
		final int encryptionStrength = 16;
		return new BCryptPasswordEncoder(encryptionStrength);
	}
}
