package com.medicalip.cafeapi.domains.users.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.commons.response.TokenResponse;
import com.medicalip.cafeapi.domains.users.dto.LoginRequest;
import com.medicalip.cafeapi.domains.users.dto.UserRequest;
import com.medicalip.cafeapi.domains.users.dto.Users;
import com.medicalip.cafeapi.domains.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	private final UserService userService; // user Service
	
	@PostMapping("/signup")
	public CommonResult signUp(@RequestBody UserRequest userRequest) {
	  return userService.findByEmail(userRequest.getEmail()).isPresent()
	      ? new CommonResult(400, "이미 가입된 회원입니다.")
	      : userService.signUp(userRequest);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequest.Login loginRequest) {
	
	  return ResponseEntity.ok().body(userService.signIn(loginRequest));
	}
	
	@GetMapping("/info")
	public ResponseEntity<List<Users>> findUser() {
	  return ResponseEntity.ok().body(userService.findUsers());
	}
}
