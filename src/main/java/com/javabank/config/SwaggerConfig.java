package com.javabank.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {
	
	public OpenAPI custoomOpenAPI() {
		
		return new OpenAPI()
				.info(new Info().title("Java Bank System REST API")
						.version("1.0")
						.description("Bank Management System developed using Spring Boot")
						.contact(new Contact()
						.name("Akshay")
						.email("your-email@example.com")));
						
								
				
				
	}

}
