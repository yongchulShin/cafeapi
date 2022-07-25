package com.medicalip.cafeapi.domains.commons.response;

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
	
	@Builder
	public TokenResponse(HttpStatus status, String message, String accessToken, String refreshToken) {
		this.resultCode = status.value();
		this.resultMessage = message;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
}
