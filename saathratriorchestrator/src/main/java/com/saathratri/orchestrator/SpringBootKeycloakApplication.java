package com.saathratri.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
	SecurityAutoConfiguration.class
	//    ,ManagementWebSecurityAutoConfiguration.class
  }, scanBasePackages = "com.saathratri.orchestrator")
public class SpringBootKeycloakApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKeycloakApplication.class, args);
	}
}
