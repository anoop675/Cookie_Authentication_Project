package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.controller;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.LoginDto;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.RegisterDto;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model.User;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security.CookieAuthenticationFilter;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service.AuthenticationService;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service.TokenService;
import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service.UserDetailsServiceIMPL;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;


@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authService;
	
	@Autowired
	UserDetailsServiceIMPL userDetailsService;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping(value = "/register", consumes = "application/json")
	public ResponseEntity<UserDetails> registerUser(@RequestBody RegisterDto registerDto){
		User registeredUser = (User)authService.register(registerDto);
		return ResponseEntity.created(URI.create("/users/" + registeredUser.getId() + "/profile")).body(registeredUser);
	}
	
	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<UserDetails> loginUser(HttpServletResponse response){
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		var user = userDetailsService.loadUserByUsername(username);
	    
		Cookie authCookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, tokenService.generateToken((User)user));
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());
        authCookie.setPath("/");

        response.addCookie(authCookie);
        //System.out.println(authCookie.getValue());

        return ResponseEntity.ok(user);			
	}
	
	@PostMapping(value = "/logout")
    public ResponseEntity<Void> logoutUser(@AuthenticationPrincipal User user, 
    		HttpServletResponse response) {
		
        SecurityContextHolder.clearContext();
        
     // Expire the authentication cookie
        Cookie authCookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, "");
        authCookie.setPath("/");
        authCookie.setMaxAge(0); // Setting the max age to 0 will expire the cookie
        authCookie.setSecure(true); // You can set this according to your needs
        authCookie.setHttpOnly(true);
        response.addCookie(authCookie);
        
        return ResponseEntity.noContent().build();
    }
}
