package com.medicalip.cafeapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.medicalip.cafeapi.domains.users.dto.UserRole;
import com.medicalip.cafeapi.domains.users.service.UserService;

@SpringBootApplication
public class CafeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeApiApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveUserRole(new UserRole(null, "ROLE_USER"));
//			userService.saveUserRole(new UserRole(null, "ROLE_MANAGER"));
//			userService.saveUserRole(new UserRole(null, "ROLE_ADMIN"));
//		};
//	}

}
