package com.medicalip.cafeapi.domains.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.commons.response.TokenResponse;
import com.medicalip.cafeapi.domains.users.dto.LoginRequest;
import com.medicalip.cafeapi.domains.users.dto.LoginRequest.Login;
import com.medicalip.cafeapi.domains.users.dto.UserRequest;
import com.medicalip.cafeapi.domains.users.dto.UserRole;
import com.medicalip.cafeapi.domains.users.dto.Users;

public interface UserService {

	Optional<Users> findByEmail(String email);
	CommonResult signUp(UserRequest userRequest);
	List<Users> findUsers();
	TokenResponse signIn(LoginRequest.Login loginRequest);
	UserRole saveUserRole(UserRole userRole);
	TokenResponse signInByRefreshToken(Login loginRequest, String refreshToken);
}
