package com.medicalip.cafeapi.domains.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalip.cafeapi.domains.auth.dto.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{
	Optional<Token> findByUsersId(long userId);
	
	Optional<Token> findByAccessToken(String accessToken);
}
