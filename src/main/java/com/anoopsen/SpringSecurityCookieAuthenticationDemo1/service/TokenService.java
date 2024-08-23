package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.User;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.repository.UserRepository;

@Service
public class TokenService {
	
	private static final String SECRET_KEY = "3A7B1F0C2E9D5A8F4B6E7C9A1D3F0E2C5B8D7A9F0C2E5D8A1F4B7E0C3A5D7"; 
	
	@Autowired
	UserRepository userRepo;
	
	public String generateToken(User user) {
		String token = user.getId()+"."+user.getUsername()+"."+calculateHmac(user);
		return token;
	}
	
	public UserDetails findByToken(String token) {
		
		 String[] parts = token.split(".");
		 
		 int userId = Integer.valueOf(parts[0]);
	     String username = parts[1];
	     String hmac = parts[2];
	     System.out.println(userId + "  " + username + "  " + hmac);

	     User user = userRepo.findByUsername(username).orElseThrow();

	     if (!hmac.equals(calculateHmac(user)) || userId != user.getId()) {
	    	 throw new RuntimeException("Invalid Cookie value");
	     }

	     return user;
	     
	}
	
	
	private String calculateHmac(User user) {
		 
	     byte[] secretKeyBytes = Objects.requireNonNull(SECRET_KEY).getBytes(StandardCharsets.UTF_8);
	     byte[] valueBytes = Objects.requireNonNull(user.getId() + "&" + user.getUsername()).getBytes(StandardCharsets.UTF_8);

	     try {
	        Mac mac = Mac.getInstance("HmacSHA512");
	        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
	        mac.init(secretKeySpec);
	        byte[] hmacBytes = mac.doFinal(valueBytes);
	        return Base64.getEncoder().encodeToString(hmacBytes);

	     } catch (NoSuchAlgorithmException | InvalidKeyException e) {
	            throw new RuntimeException(e);
	     }
	}

}
