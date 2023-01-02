package com.medicalip.cafeapi.domains.menu.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medicalip.cafeapi.domains.menu.dto.Menu;
import com.medicalip.cafeapi.domains.menu.dto.MenuCategory;
import com.medicalip.cafeapi.domains.menu.repo.MenuCategoryRepository;
import com.medicalip.cafeapi.domains.menu.repo.MenuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final MenuCategoryRepository menuCategoryRepository;
	
	@Override
	public List<Menu> getMenuList() {
		// TODO Auto-generated method stub
		return Arrays.asList(
				menuRepository.findAll()
				.stream()
				.filter(m -> m.getUseYn().equals("Y"))
				.toArray(Menu[]::new));
	}
	
	@Override
	public List<MenuCategory> getMenuCategoryList() {
		// TODO Auto-generated method stub
		return  menuCategoryRepository.findAll();
	}

}
