package com.medicalip.cafeapi.domains.users.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.commons.response.TokenResponse;
import com.medicalip.cafeapi.domains.commons.util.EncryptUtil;
import com.medicalip.cafeapi.domains.users.dto.LoginRequest;
import com.medicalip.cafeapi.domains.users.dto.UserRequest;
import com.medicalip.cafeapi.domains.users.dto.Users;
import com.medicalip.cafeapi.domains.users.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	private final UserService userService; // user Service
	
	@PostMapping("/signup")
	@Operation(summary = "회원가입", description = "회원가입을 한다.")
	public CommonResult signUp(@RequestBody UserRequest userRequest) {
	  return userService.findByEmail(userRequest.getEmail()).isPresent()
	      ? new CommonResult(400, "이미 가입된 회원입니다.")
	      : userService.signUp(userRequest);
	}
	
	@PostMapping("/signin")
	@Operation(summary = "로그인", description = "로그인을 한다.")
	public ResponseEntity<?> signIn(@RequestBody LoginRequest.Login loginRequest) {
	  return ResponseEntity.ok().body(userService.signIn(loginRequest));
	}
	
	@GetMapping("/info")
	public ResponseEntity<List<Users>> findUser() {
	  return ResponseEntity.ok().body(userService.findUsers());
	}
	
//	@GetMapping("/chPass")
//	public String chPass() {
//		String pass = null;
//		try {
//			pass = EncryptUtil.sha512("1234");
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return pass;
//	}
}
