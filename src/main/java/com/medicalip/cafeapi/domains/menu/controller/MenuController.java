package com.medicalip.cafeapi.domains.menu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalip.cafeapi.domains.menu.dto.Menu;
import com.medicalip.cafeapi.domains.menu.dto.MenuCategory;
import com.medicalip.cafeapi.domains.menu.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/menu")
@Tag(name = "3. Menu", description = "메뉴/메뉴카테고리 api")
public class MenuController {
	private final MenuService menuService;
	
	@GetMapping("/menuList")
	@Operation(summary = "메뉴리스트", description = "메뉴리스트를 조회한다.")
	public ResponseEntity<List<Menu>> getMenuList(){
		return ResponseEntity.ok().body(menuService.getMenuList());
	}
	
	@GetMapping("/menuCategoryList")
	@Operation(summary = "메뉴 카테고리 리스트", description = "메뉴 카테고리 리스트를 조회한다.")
	public ResponseEntity<List<MenuCategory>> getMenuCategoryList(){
		return ResponseEntity.ok().body(menuService.getMenuCategoryList());
	}
}
