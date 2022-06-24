package com.medicalip.cafe.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicalip.cafe.repository.ExpiredRefreshRepository;

@Service
public class ExpiredRefreshTokenService {
	@Autowired
    private ExpiredRefreshRepository expiredRefreshRepository;

    public boolean isExpiredToken(String token) {
        return expiredRefreshRepository.existsByToken(token);
    }

    public ExpiredRefreshToken addExpiredToken(String token) {
        ExpiredRefreshToken saveToken = ExpiredRefreshToken.builder()
            .token(token)
            .build();
        return expiredRefreshRepository.save(saveToken);
    }
}
