package com.medicalip.cafeapi.domains.commons.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
	private int resultCode;
	private String resultMessage;
	private String accessToken;
	private String refreshToken;
	private List<String> roles;
	
	@Builder
	public TokenResponse(HttpStatus status, String message, String accessToken, String refreshToken, List<String> rolesList) {
		this.resultCode = status.value();
		this.resultMessage = message;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.roles = rolesList;
	}
	
}
