package com.medicalip.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalip.cafe.config.security.ExpiredRefreshToken;

public interface ExpiredRefreshRepository extends JpaRepository<ExpiredRefreshToken, Long>{
	boolean existsByToken(String token); 
}
