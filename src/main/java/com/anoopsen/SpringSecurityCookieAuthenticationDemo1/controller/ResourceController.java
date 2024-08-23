package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/resources")
public class ResourceController {
	
	@GetMapping(value = "/resource1")
	public String getResource1() {
		return "Resource1 accessed :)";
	}

}
