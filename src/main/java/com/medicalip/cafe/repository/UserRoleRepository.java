package com.medicalip.cafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalip.cafe.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	UserRole findByRoleName(String userRoleName);
	List<UserRole> findAll();
	UserRole save(UserRole userRole);

}
