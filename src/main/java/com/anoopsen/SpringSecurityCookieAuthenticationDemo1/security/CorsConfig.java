package com.anoopsen.SpringSecurityCookieAuthenticationDemo1.security;
/*
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc  //used to enable spring MVC features
public class CorsConfig implements WebMvcConfigurer { //'WebMvcConfigurer' is an interface used to customize the configurations of Spring MVC

    private static final Long MAX_AGE = 3600L;

    @Override
    public void addCorsMappings(CorsRegistry registry) { //allows you to perform Cross-Origin Resource Sharing configurations with a mapped frontend app
    	
        registry
        		.addMapping("/**")    //CORS configurations is applied to all URL paths in the application
        		.allowedHeaders(			//Headers allowed to enter the application from the frontend application's CORS requests
	                    HttpHeaders.AUTHORIZATION,
	                    HttpHeaders.CONTENT_TYPE,
	                    HttpHeaders.ACCEPT
                    )
        		.allowedMethods(            //The HTTP methods allowed to be performed from the frontend application's CORS requests
	                    HttpMethod.GET.name(),
	                    HttpMethod.POST.name(),
	                    HttpMethod.PUT.name(),
	                    HttpMethod.DELETE.name()
	                )
        		.maxAge(MAX_AGE)     //It sets the maximum age (in seconds) for which the CORS configuration is cached by the browser. In this case, it's set to 3600 seconds (1 hour).
        		.allowedOrigins("*")     //any website, from any domain, is allowed to make CORS requests to this application
        		//.allowedOrigins("https://example.com")
        		.allowCredentials(true);   //This allows the use of cookies or other credentials during CORS requests.
    }
}
*/