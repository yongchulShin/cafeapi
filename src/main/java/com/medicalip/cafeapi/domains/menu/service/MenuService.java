package com.medicalip.cafeapi.domains.menu.service;

import java.util.List;

import com.medicalip.cafeapi.domains.menu.dto.Menu;
import com.medicalip.cafeapi.domains.menu.dto.MenuCategory;

public interface MenuService {

	List<Menu> getMenuList();

	List<MenuCategory> getMenuCategoryList();

}
