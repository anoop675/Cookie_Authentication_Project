package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonSerialize
//@JsonDeserialize
public class LoginDto {
	
	//@JsonProperty("username")
	private String username;
	
	//@JsonProperty("password")
	private String password;
	
}