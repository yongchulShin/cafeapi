package com.medicalip.cafeapi.domains.users.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalip.cafeapi.domains.users.dto.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	Optional<Users> findByEmail(String email);
	boolean existsByEmail(String email);
}
