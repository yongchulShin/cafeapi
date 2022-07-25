package com.medicalip.cafeapi.domains.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalip.cafeapi.domains.auth.service.TokenService;
import com.medicalip.cafeapi.domains.commons.jwt.TokenUtils;
import com.medicalip.cafeapi.domains.commons.response.SingleResult;
import com.medicalip.cafeapi.domains.users.dto.LoginRequest;
import com.medicalip.cafeapi.domains.users.dto.Users;
import com.medicalip.cafeapi.domains.users.repo.UsersRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired private final TokenService tokenService;
	
	@PostMapping("/access/generate")
	public SingleResult generateJwtToken(@RequestBody LoginRequest.Login loginRequest) {
		return tokenService.generateJwtToken(loginRequest.getEmail());
	}
	
}
