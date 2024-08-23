package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
	
	private String email;
	
	private String username;
	
	private String password;

}
