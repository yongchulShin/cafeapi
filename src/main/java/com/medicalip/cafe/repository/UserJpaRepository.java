package com.medicalip.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalip.cafe.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

	boolean existsByEmail(String email);
}
