package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.anoopsen.SpringSecurityCookieAuthenticationDemo1.service.UserDetailsServiceIMPL;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	
	@Autowired
	UsernamePasswordAuthFilter usernamePasswordAuthFilter;
	
	@Autowired
	CookieAuthenticationFilter cookieAuthenticationFilter;
	
	@Autowired
	UserDetailsServiceIMPL userDetailsService;
	
	@Autowired
	UserAuthenticationProvider userAuthenticationProvider;
	
	@Autowired
	PasswordConfig passwordConfig;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(
					httpRequest -> httpRequest
										.requestMatchers(HttpMethod.POST)
										.permitAll()
										.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/login"))
										.permitAll()
										.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/register"))
										.permitAll()
										.anyRequest()
										.authenticated()
	
			)
			.addFilterBefore(usernamePasswordAuthFilter, BasicAuthenticationFilter.class)
			.addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthFilter.class)
			.sessionManagement(
					session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authenticationProvider(userAuthenticationProvider)
			//.authenticationProvider(this.authenticationProvider())
			.exceptionHandling(
					exception -> exception.authenticationEntryPoint(userAuthenticationEntryPoint)
			)
			.logout(
					logout -> logout.deleteCookies(CookieAuthenticationFilter.COOKIE_NAME)
			);
		
		DefaultSecurityFilterChain filterChain = http.build();
		return filterChain;
		
	}
	
	/*
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		
		daoAuthProvider.setUserDetailsService(this.userDetailsService);
		daoAuthProvider.setPasswordEncoder(this.passwordEncoder());
		
		return daoAuthProvider;
	}*/
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		
		return config.getAuthenticationManager();
	}

}
