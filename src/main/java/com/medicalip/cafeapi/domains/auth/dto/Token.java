package com.medicalip.cafeapi.domains.auth.dto;

import java.util.Date;

import javax.persistence.*;

import com.medicalip.cafeapi.domains.users.dto.Users;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Table(name = "token")
@Entity
public class Token {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String accessToken;
	private String refreshToken;
	
	private Date expireDt;
	
	@ManyToOne
	@JoinColumn(name = "usersId")
	private Users users;
	
	@Builder
	public Token(String accessToken, String refreshToken, Users users, Date expireDt) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.users = users;
		this.expireDt = expireDt;
	}
	public void refreshUpdate(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public void accessUpdate(String accessToken) {
		this.accessToken = accessToken;
	}
}
