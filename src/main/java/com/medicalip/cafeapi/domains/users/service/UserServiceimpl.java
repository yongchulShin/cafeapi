package com.medicalip.cafeapi.domains.users.service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medicalip.cafeapi.domains.auth.dto.Token;
import com.medicalip.cafeapi.domains.auth.repo.TokenRepository;
import com.medicalip.cafeapi.domains.commons.jwt.TokenUtils;
import com.medicalip.cafeapi.domains.commons.response.CommonResult;
import com.medicalip.cafeapi.domains.commons.response.Response;
import com.medicalip.cafeapi.domains.commons.response.TokenResponse;
import com.medicalip.cafeapi.domains.commons.util.Constants;
import com.medicalip.cafeapi.domains.commons.util.EncryptUtil;
import com.medicalip.cafeapi.domains.users.dto.LoginRequest;
import com.medicalip.cafeapi.domains.users.dto.LoginRequest.Login;
import com.medicalip.cafeapi.domains.users.dto.UserRequest;
import com.medicalip.cafeapi.domains.users.dto.UserRole;
import com.medicalip.cafeapi.domains.users.dto.Users;
import com.medicalip.cafeapi.domains.users.repo.UserRoleRepository;
import com.medicalip.cafeapi.domains.users.repo.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceimpl implements UserService{
	
	private final TokenUtils tokenUtils;
	private final UsersRepository usersRepository;
	private final UserRoleRepository userRoleRepository;
	private final TokenRepository tokenRepository;
//	private final PasswordEncoder passwordEncoder; // ???????????? ?????????
	private final Response res;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final AuthenticationManager authenticationManager;
	
	public CommonResult signUp(UserRequest userRequest) {
		System.out.println("signUp Function");
	  Users users = null;
	try {
		users = usersRepository.save(
	    		  Users.builder()
		    		.email(userRequest.getEmail())
//		            .password(userRequest.getPassword())
		            .password(EncryptUtil.sha512(userRequest.getPassword()))
	                .name(userRequest.getName())
	                .roles(Collections.singletonList(userRequest.getRole()))
	                .edtTime(LocalDateTime.now())
	                .regTime(LocalDateTime.now())
	                .build());
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	  String accessToken = tokenUtils.generateJwtToken(users);
	  String refreshToken = tokenUtils.saveRefreshToken(users);
	  
	  tokenRepository.save(
	      Token.builder()
	      	.users(users)
	      	.accessToken(accessToken)
	      	.refreshToken(refreshToken)
	      	.expireDt(tokenUtils.createExpireDate(Constants.REFRESH_TOKEN_VALID_TIME))
	      	.build());
	  
	  tokenUtils.generateJwtToken(users);
	  return new CommonResult(200, "??????????????? ?????????????????????.");
	}
	
	public TokenResponse signIn(LoginRequest.Login login) {
        try {
        	Users users = usersRepository.findByEmail(login.getEmail()).get();
        	System.out.println("users :: " + users.getEmail());
        	System.out.println("users :: " + users.getRoles());
//        	tokenUtils.generateJwtToken(users);
        	// 1. Login ID/PW ??? ???????????? Authentication ?????? ??????
        	// ?????? authentication ??? ?????? ????????? ???????????? authenticated ?????? false
        	UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();
        	System.out.println("authenticationToken :: " + authenticationToken);
        	
        	// 2. ?????? ?????? (????????? ???????????? ??????)??? ??????????????? ??????
            // authenticate ???????????? ????????? ??? CustomUserDetailsService ?????? ?????? loadUserByUsername ???????????? ??????
        	Authentication authentication = authenticationManager.authenticate(authenticationToken);
        	
            System.out.println("authentication :: " + authentication.getPrincipal());
        	// 3. ?????? ????????? ???????????? JWT ?????? ??????
        	Token tokenInfo = tokenUtils.generateToken(authentication);
        	
        	Optional<Token> updateOp = tokenRepository.findByUsersId(users.getId());
        	
        	// 4. RefreshToken ?????? (expirationTime ????????? ?????? ?????? ?????? ??????)
        	if(updateOp.stream().count() != 0) { // RefreshToken ??????
        		Token updateToken = updateOp.get();
        		updateToken.setAccessToken(tokenInfo.getAccessToken());
        		updateToken.setExpireDt(tokenUtils.createExpireDate(Constants.REFRESH_TOKEN_VALID_TIME));
        		tokenRepository.save(updateToken);
        	}else {
        		tokenRepository.save( // ??????(??????)
        				Token.builder()
        				.accessToken(tokenInfo.getAccessToken())
        				.refreshToken(tokenInfo.getRefreshToken())
        				.users(users)
        				.expireDt(tokenUtils.createExpireDate(Constants.REFRESH_TOKEN_VALID_TIME))
        				.build());
        	}
        	
        	return TokenResponse.builder()
        			.status(HttpStatus.OK)
        			.message("???????????? ??????????????????.")
        			.accessToken(tokenInfo.getAccessToken())
        			.refreshToken(tokenInfo.getRefreshToken())
        			.rolesList(users.getRoles())
        			.build();
        }catch (BadCredentialsException e) {
			// TODO: handle exception
        	return TokenResponse.builder()
        			.status(HttpStatus.FORBIDDEN)
    				.message("??????????????? ???????????? ????????????.")
        			.build();
		}catch (NoSuchElementException e) {
			// TODO: handle exception
        	return TokenResponse.builder()
        			.status(HttpStatus.BAD_REQUEST)
    				.message("???????????? ?????? ???????????????.")
        			.build();
		}
	}
	
	public List<Users> findUsers() {
	  return usersRepository.findAll();
	}
		
	public Optional<Users> findByEmail(String email) {
		// TODO Auto-generated method stub
		return usersRepository.findByEmail(email);
	}

	public UserRole saveUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		log.info("Saving new role {} to the db", userRole.getRoleName());
        return userRoleRepository.save(userRole);
	}

	@Override
	public TokenResponse signInByRefreshToken(Login loginRequest, String refreshToken) {
		// TODO Auto-generated method stub'
		System.out.println("[refreshToken] :: " + refreshToken);
		try {
			Users users = usersRepository.findByEmail(loginRequest.getEmail()).get();
			String accessToken = tokenUtils.generateJwtToken(users);
			
			Token updateToken = tokenRepository.findByUsersId(users.getId()).get();
			updateToken.setAccessToken(accessToken);
			updateToken.setUsers(users);
			tokenRepository.save(updateToken);
			
			return TokenResponse.builder()
        			.status(HttpStatus.OK)
        			.message("???????????? ??????????????????.")
        			.accessToken(updateToken.getAccessToken())
        			.refreshToken(updateToken.getRefreshToken())
        			.rolesList(users.getRoles())
        			.build();
        }catch (BadCredentialsException e) {
			// TODO: handle exception
        	return TokenResponse.builder()
        			.status(HttpStatus.FORBIDDEN)
    				.message("??????????????? ???????????? ????????????.")
//            			.accessToken(tokenInfo.getAccessToken())
//            			.refreshToken(tokenInfo.getRefreshToken())
        			.build();
		}catch (NoSuchElementException e) {
			// TODO: handle exception
        	return TokenResponse.builder()
        			.status(HttpStatus.BAD_REQUEST)
    				.message("???????????? ?????? ???????????????.")
//            			.accessToken(tokenInfo.getAccessToken())
//            			.refreshToken(tokenInfo.getRefreshToken())
        			.build();
		}
	}
	
}
