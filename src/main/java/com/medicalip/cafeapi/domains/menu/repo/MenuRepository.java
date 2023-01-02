package com.medicalip.cafeapi.domains.menu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalip.cafeapi.domains.menu.dto.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
