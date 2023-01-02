package com.medicalip.cafeapi.domains.menu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicalip.cafeapi.domains.menu.dto.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}
